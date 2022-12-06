package com.myth.system.controller;

import com.myth.common.result.JsonResult;
import com.myth.system.bean.SysPermission;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@CrossOrigin
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private DynamicDataSource dynamicDataSource;

    @PostMapping("/getSysPermissionByMenuId")
    public JsonResult getSysPermissionByMenuId(Integer menuId, DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysPermissionService.getSysPermissionByMenuId(menuId);
    }
    @PostMapping("/addSysPermission")
    public JsonResult addSysPermission(SysPermission sysPermission,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysPermissionService.addSysPermission(sysPermission);
    }
    @PostMapping("/editSysPermission")
    public JsonResult editSysPermission(SysPermission sysPermission,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysPermissionService.editSysPermission(sysPermission);
    }
    @PostMapping("/deleteSysPermissionById")
    public JsonResult deleteSysPermissionById(Integer id,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysPermissionService.deleteSysPermissionById(id);
    }
}
