package com.myth.system.service;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysPermission;
import com.myth.system.mapper.SysPermissionMapper;
import com.myth.system.mapper.SysRequestPathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRequestPathMapper sysRequestPathMapper;
    public JsonResult getSysPermissionByMenuId(Integer menuId){
        return ResultTool.success(sysPermissionMapper.getSysPermissionByMenuId(menuId));
    }
    public JsonResult addSysPermission(SysPermission sysPermission){
        sysPermissionMapper.addSysPermission(sysPermission);
        return ResultTool.success(sysPermissionMapper.getSysPermissionByMenuId(sysPermission.getMenuId()));
    }
    public JsonResult editSysPermission(SysPermission sysPermission){
        sysPermissionMapper.editSysPermission(sysPermission);
        return ResultTool.success(sysPermissionMapper.getSysPermissionByMenuId(sysPermission.getMenuId()));
    }
    public JsonResult deleteSysPermissionById(Integer id){
        SysPermission sysPermission = sysPermissionMapper.getSysPermissionById(id);
        sysPermissionMapper.deleteSysPermissionById(sysPermission.getId());
        sysRequestPathMapper.deleteSysRequestPathByPermissionId(sysPermission.getId());
        return ResultTool.success(sysPermissionMapper.getSysPermissionByMenuId(sysPermission.getMenuId()));
    }
}
