package com.myth.system.controller;

import com.myth.common.result.JsonResult;
import com.myth.system.bean.SysPermission;
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

    @PostMapping("/getSysPermissionByMenuId")
    public JsonResult getSysPermissionByMenuId(Integer menuId){
        return sysPermissionService.getSysPermissionByMenuId(menuId);
    }
    @PostMapping("/addSysPermission")
    public JsonResult addSysPermission(SysPermission sysPermission){
        return sysPermissionService.addSysPermission(sysPermission);
    }
    @PostMapping("/editSysPermission")
    public JsonResult editSysPermission(SysPermission sysPermission){
        return sysPermissionService.editSysPermission(sysPermission);
    }
    @PostMapping("/deleteSysPermissionById")
    public JsonResult deleteSysPermissionById(Integer id){
        return sysPermissionService.deleteSysPermissionById(id);
    }
}
