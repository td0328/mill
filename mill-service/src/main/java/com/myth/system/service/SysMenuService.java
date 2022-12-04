package com.myth.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysMenu;
import com.myth.system.bean.SysPermission;
import com.myth.system.bean.SysRolePermissionRelation;
import com.myth.system.mapper.SysMenuMapper;
import com.myth.system.mapper.SysPermissionMapper;
import com.myth.system.mapper.SysRolePermissionRelationMapper;
import com.myth.system.vo.SysMenuVo;
import com.myth.system.vo.TreeSysPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRolePermissionRelationMapper sysRolePermissionRelationMapper;


    public JsonResult getAllSysMenu(){
        List<SysMenuVo> list = sysMenuMapper.getAllSysMenu();
        return ResultTool.success(list);
    }
    public JsonResult getTreeSysMenu(Integer id){
        HashMap<String,Object> map = new HashMap<>();
        List<TreeSysPermissionVo> treeList = sysMenuMapper.getTreeSysMenu();
        map.put("tree",treeList);
        if(id!=null){
            List<String> defaultList = new ArrayList<>();
            List<SysRolePermissionRelation> sysRolePermissionRelationList = sysRolePermissionRelationMapper.getSysRolePermissionRelationByRoleId(id);
            for (SysRolePermissionRelation sysRolePermissionRelation:sysRolePermissionRelationList){
                defaultList.add("permission_"+sysRolePermissionRelation.getPermissionId());
            }
            map.put("default",defaultList);
        }
        return ResultTool.success(map);
    }
    public JsonResult addSysMenu(SysMenu sysMenu){
        sysMenuMapper.addSysMenu(sysMenu);
        List<SysMenuVo> list = sysMenuMapper.getAllSysMenu();
        return ResultTool.success(list);
    }
    public JsonResult editSysMenu(SysMenu sysMenu){
        sysMenuMapper.editSysMenu(sysMenu);
        List<SysMenuVo> list = sysMenuMapper.getAllSysMenu();
        return ResultTool.success(list);
    }
    public JsonResult deleteSysMenuByIds(Integer[] ids){
        int i = sysMenuMapper.deleteSysMenuByIds(ids);
        sysMenuMapper.deleteSysMenuByPid(ids);
        List<SysMenuVo> list = sysMenuMapper.getAllSysMenu();
        return ResultTool.success(list);
    }
}
