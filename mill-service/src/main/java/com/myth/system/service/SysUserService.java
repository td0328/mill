package com.myth.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.common.utils.ToolUtils;
import com.myth.system.bean.SysPermission;
import com.myth.system.bean.SysRole;
import com.myth.system.bean.SysUser;
import com.myth.system.bean.SysUserRoleRelation;
import com.myth.system.mapper.*;
import com.myth.system.vo.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleRelationMapper sysUserRoleRelationMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    public Page getSysUserByQueryWrapper(Page<?> page, QueryWrapper<?> ew){
        Page<?> pageResult = sysUserMapper.getSysUserByQueryWrapper(page, ew);
        return pageResult;
    }
    public JsonResult getAllSysRoleByUserId(Integer userId){
        HashMap<String,Object> map = new HashMap<>();
        List<SysRole> sysRoleList = sysRoleMapper.getAllSysRole();
        List<Integer> list = new ArrayList<>();
        map.put("checkboxSysRole",sysRoleList);
        if(userId!=null){
            List<SysUserRoleRelation> sysUserRoleRelationList = sysUserRoleRelationMapper.getSysUserRoleRelationByUserId(userId);
            for (SysUserRoleRelation sysUserRoleRelation:sysUserRoleRelationList){
                list.add(sysUserRoleRelation.getRoleId());
            }
        }
        map.put("checkboxSysRoleValue",list);
        return ResultTool.success(map);
    }
    @Transactional
    public JsonResult addSysUser(SysUser sysUser, Integer[] roleIds,String myAccount){
        if(sysUserMapper.selectOne(sysUser.getAccount())!=null)return ResultTool.fail("该账号已存在");
        sysUser.setCreateUser(sysUserMapper.selectOne(myAccount).getId());
        sysUser.setCreateTime(new Date());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        sysUser.setPassword(encode);
        sysUser.setEnabled(true);
        sysUserMapper.addSysUser(sysUser);
        for (Integer roleId:roleIds){
            SysUserRoleRelation sysUserRoleRelation = new SysUserRoleRelation();
            sysUserRoleRelation.setUserId(sysUser.getId());
            sysUserRoleRelation.setRoleId(roleId);
            sysUserRoleRelationMapper.addSysUserRoleRelation(sysUserRoleRelation);
        }
        return ResultTool.success();
    }
    @Transactional
    public JsonResult editSysUser(SysUser sysUser, Integer[] roleIds,String myAccount){
        if(sysUserMapper.getSysUserByIdAndAccount(sysUser).size()>0)return ResultTool.fail("该账号已存在");
        sysUser.setUpdateUser(sysUserMapper.selectOne(myAccount).getId());
        sysUser.setUpdateTime(new Date());
        sysUserMapper.editSysUser(sysUser);
        HashMap<String,String> roleIdMap = ToolUtils.arrayToMap(roleIds);
        List<SysUserRoleRelation> list = sysUserRoleRelationMapper.getSysUserRoleRelationByUserId(sysUser.getId());
        for(SysUserRoleRelation sysUserRoleRelation:list){
            if(roleIdMap.get(sysUserRoleRelation.getRoleId())==null)sysUserRoleRelationMapper.deleteSysUserRoleRelationById(sysUserRoleRelation.getId());
        }
        for (Integer roleId:roleIds){
            if(sysUserRoleRelationMapper.getSysUserRoleRelationByUserIdAndRoleId(sysUser.getId(),roleId).size()>0)continue;
            SysUserRoleRelation sysUserRoleRelation = new SysUserRoleRelation();
            sysUserRoleRelation.setUserId(sysUser.getId());
            sysUserRoleRelation.setRoleId(roleId);
            sysUserRoleRelationMapper.addSysUserRoleRelation(sysUserRoleRelation);
        }
        return ResultTool.success();
    }
    @Transactional
    public JsonResult deleteSysUserByIds(Integer[] ids){
        sysUserMapper.deleteSysUserByIds(ids);
        sysUserRoleRelationMapper.deleteSysUserRoleRelationByUserIds(ids);
        return ResultTool.success();
    }
    public JsonResult resetPassword(SysUser sysUser){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        sysUser.setPassword(encode);
        sysUserMapper.resetPassword(sysUser);
        return ResultTool.success();
    }
    public JsonResult editPassword (String myAccount,String oidPassword,String newPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        SysUser sysUser = sysUserMapper.selectOne(myAccount);
        if(sysUser==null)return ResultTool.fail("用户不存在");

        boolean flag = passwordEncoder.matches(oidPassword,sysUser.getPassword());
        if(flag){
            sysUser.setPassword(passwordEncoder.encode(newPassword));
            sysUserMapper.resetPassword(sysUser);
            return ResultTool.success();
        }else{
            return ResultTool.fail("旧密码有误");
        }
    }
    public JsonResult getMySysUserAndMySysMenuAndMySysPermissionByUserId(Integer userId){
        SysUser sysUser = sysUserMapper.getSysUserById(userId);
        List<SysMenuVo> list = sysMenuMapper.getSysMenuByUserId(sysUser.getId());
        JSONArray menuJson = JSONArray.parseArray(JSONArray.toJSONString(list));
        for(int i=0;i<menuJson.size();i++){
            JSONObject subMenu = menuJson.getJSONObject(i);
            String index = subMenu.getString("id");
            String icon = subMenu.getString("icon");
            String title = subMenu.getString("title");
            JSONArray menuItemList = subMenu.getJSONArray("children");
            for(int j=0;j<menuItemList.size();j++){
                JSONObject menuItem = menuItemList.getJSONObject(j);
                Integer type = menuItem.getInteger("type");
                String url = "";
                String name = "";
                String path = "/";
                if(type==0){
                    name = menuItem.getString("tableName");
                    path+=menuItem.getString("tableName");
                    url = "components/table/"+menuItem.getString("tableName");
                }
                if(type==1){
                    name = menuItem.getString("pageName");
                    path+=menuItem.getString("pageName");
                    url = "components/custom/"+menuItem.getString("pageName");
                }
                if(type==2){
                    name = menuItem.getString("pageName");
                    path+=menuItem.getString("pageName");
                    url = "components/system/"+menuItem.getString("pageName");
                }
                String menuItemTitle = menuItem.getString("title");
                String menuItemIcon = menuItem.getString("icon");
                menuItem.clear();
                menuItem.put("url",url);
                menuItem.put("name",name);
                menuItem.put("path",path);
                menuItem.put("title",menuItemTitle);
                menuItem.put("icon",menuItemIcon);
                menuItemList.set(j,menuItem);
            }
            subMenu.clear();
            subMenu.put("index",index);
            subMenu.put("icon",icon);
            subMenu.put("title",title);
            subMenu.put("children",menuItemList);
            menuJson.set(i,subMenu);
        }
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("menu",menuJson);//当前用户的菜单
        List<SysPermission> sysPermissionList =  sysPermissionMapper.getMySysPermissionByUserId(sysUser.getId());
        resultMap.put("permission",sysPermissionList);//当前用户按钮权限
        resultMap.put("sysUser",sysUser);//当前用户的菜单
        return ResultTool.success(resultMap);
    }
    public JsonResult setEnabled(SysUser sysUser){
        sysUserMapper.setEnabled(sysUser);
        return ResultTool.success();
    }
}
