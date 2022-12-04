package com.myth.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.*;
import com.myth.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    SysRolePermissionRelationMapper sysRolePermissionRelationMapper;
    @Autowired
    private SysRoleMenuRelationMapper sysRoleMenuRelationMapper;
    @Autowired
    private SysUserRoleRelationMapper sysUserRoleRelationMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Transactional
    public JsonResult getAllSysRole(){
        return ResultTool.success(sysRoleMapper.getAllSysRole());
    }
    @Transactional
    public Page getSysRoleByQueryWrapper(Page<?> page,QueryWrapper<?> ew){
        Page<?> pageResult = sysRoleMapper.getSysRoleByQueryWrapper(page, ew);
        return pageResult;
    }
    @Transactional
    public JsonResult addSysRole(SysRole sysRole, String menuIds[]){
        sysRoleMapper.addSysRole(sysRole);
        for(String id:menuIds){
            if(!id.contains("permission_")){
                SysMenu sysMenu = sysMenuMapper.getSysMenuById(Integer.parseInt(id));
                //添加默认查询权限
                if(sysMenu.getPid()!=null){
                    SysPermission sysPermission = sysPermissionMapper.getGetSysPermissionByMenuId(sysMenu.getId());
                    SysRolePermissionRelation sysRolePermissionRelation = new SysRolePermissionRelation();
                    sysRolePermissionRelation.setRoleId(sysRole.getId());
                    sysRolePermissionRelation.setPermissionId(sysPermission.getId());
                    sysRolePermissionRelationMapper.addSysRolePermissionRelation(sysRolePermissionRelation);
                }
                //添加菜单
                SysRoleMenuRelation sysRoleMenuRelation = new SysRoleMenuRelation();
                sysRoleMenuRelation.setMenuId(sysMenu.getId());
                sysRoleMenuRelation.setRoleId(sysRole.getId());
                sysRoleMenuRelationMapper.addSysRoleMenuRelation(sysRoleMenuRelation);
            }else{
                //如果该权限已存在，则跳出
                //添加权限
                id = id.replaceAll("permission_","");
                SysRolePermissionRelation sysRolePermissionRelation = new SysRolePermissionRelation();
                sysRolePermissionRelation.setRoleId(sysRole.getId());
                sysRolePermissionRelation.setPermissionId(Integer.parseInt(id));
                sysRolePermissionRelationMapper.addSysRolePermissionRelation(sysRolePermissionRelation);
            }
        }
        return ResultTool.success();
    }
    @Transactional
    public JsonResult deleteSysRole(Integer[] ids){
        for(Integer id:ids){
            List<SysUserRoleRelation> list = sysUserRoleRelationMapper.getSysUserRoleRelationByRoleId(id);
            if(list.size()>0){
                SysRole sysRole = sysRoleMapper.getSysRoleById(list.get(0).getRoleId());
                SysUser sysUser = sysUserMapper.getSysUserById(list.get(0).getUserId());
                return ResultTool.fail("角色名："+sysRole.getRoleName()+"在用户名："+sysUser.getUserName()+"中有应用无法删除");
            }
        }
        sysRoleMenuRelationMapper.deleteSysRoleMenuRelation(ids);
        sysRolePermissionRelationMapper.deleteSysRolePermissionRelation(ids);
        sysRoleMapper.deleteSysRole(ids);
        return ResultTool.success();
    }
    @Transactional
    public JsonResult editSysRole(SysRole sysRole, String[] menuIds){

        HashMap<String,String> menuIdMap = new HashMap<>();
        HashMap<String,String> permissionMap = new HashMap<>();
        for(String id:menuIds){
            if(!id.contains("permission_")){
                menuIdMap.put(id,id);
            }else{
                id = id.replaceAll("permission_","");
                permissionMap.put(id,id);
            }
        }
        List<SysRoleMenuRelation> sysRoleMenuRelationList = sysRoleMenuRelationMapper.getSysRoleMenuRelationByRoleId(sysRole.getId());
        for(SysRoleMenuRelation sysRoleMenuRelation:sysRoleMenuRelationList){
            if(menuIdMap.get(sysRoleMenuRelation.getMenuId().toString())==null){
                sysRoleMenuRelationMapper.deleteSysRoleMenuRelationById(sysRoleMenuRelation.getId());
            }
        }
        List<SysRolePermissionRelation> sysRolePermissionRelationList = sysRolePermissionRelationMapper.getSysRolePermissionRelationByRoleId(sysRole.getId());
        for(SysRolePermissionRelation sysRolePermissionRelation:sysRolePermissionRelationList){
            if(permissionMap.get(sysRolePermissionRelation.getPermissionId().toString())==null){
                sysRolePermissionRelationMapper.deleteSysRolePermissionRelationById(sysRolePermissionRelation.getId());
            }
        }
        for(String id:menuIds){
            if(!id.contains("permission_")){
                SysMenu sysMenu = sysMenuMapper.getSysMenuById(Integer.parseInt(id));
                //添加默认查询权限
                if(sysMenu.getPid()!=null){
                    SysPermission sysPermission = sysPermissionMapper.getGetSysPermissionByMenuId(sysMenu.getId());
                    if(sysRolePermissionRelationMapper.getSysRolePermissionRelationByRoleIdAndPermissionId(sysRole.getId(),sysPermission.getId()).size()==0){
                        SysRolePermissionRelation sysRolePermissionRelation = new SysRolePermissionRelation();
                        sysRolePermissionRelation.setRoleId(sysRole.getId());
                        sysRolePermissionRelation.setPermissionId(sysPermission.getId());
                        sysRolePermissionRelationMapper.addSysRolePermissionRelation(sysRolePermissionRelation);
                    }
                }
                //如果该权限已存在，则跳出
                if(sysRoleMenuRelationMapper.getSysRoleMenuRelationByRoleIdAndMenuId(sysRole.getId(),sysMenu.getId()).size()>0)continue;
                //添加菜单
                SysRoleMenuRelation sysRoleMenuRelation = new SysRoleMenuRelation();
                sysRoleMenuRelation.setMenuId(sysMenu.getId());
                sysRoleMenuRelation.setRoleId(sysRole.getId());
                sysRoleMenuRelationMapper.addSysRoleMenuRelation(sysRoleMenuRelation);
            }else{
                id = id.replaceAll("permission_","");
                //如果该权限已存在，则跳出
                if(sysRolePermissionRelationMapper.getSysRolePermissionRelationByRoleIdAndPermissionId(sysRole.getId(),Integer.parseInt(id)).size()>0)continue;
                //添加权限
                SysRolePermissionRelation sysRolePermissionRelation = new SysRolePermissionRelation();
                sysRolePermissionRelation.setRoleId(sysRole.getId());
                sysRolePermissionRelation.setPermissionId(Integer.parseInt(id));
                sysRolePermissionRelationMapper.addSysRolePermissionRelation(sysRolePermissionRelation);
            }
        }
        sysRoleMapper.editSysRole(sysRole);
        return ResultTool.success();
    }
}
