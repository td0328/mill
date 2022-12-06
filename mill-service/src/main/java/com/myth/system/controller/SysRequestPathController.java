package com.myth.system.controller;

import com.myth.common.result.JsonResult;
import com.myth.system.bean.SysPermission;
import com.myth.system.bean.SysRequestPath;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysPermissionService;
import com.myth.system.service.SysRequestPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
@CrossOrigin
public class SysRequestPathController {
    @Autowired
    private SysRequestPathService sysRequestPathService;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @PostMapping("/getSysRequestPathByPermissionId")
    public JsonResult getSysRequestPathByPermissionId(Integer permissionId,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysRequestPathService.getSysRequestPathByPermissionId(permissionId);
    }
    @PostMapping("/addSysRequestPath")
    public JsonResult addSysRequestPath(SysRequestPath sysRequestPath, DataSourceVo dataSourceVo,String urls){
        sysRequestPath.setUrl(urls);
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysRequestPathService.addSysRequestPath(sysRequestPath);
    }
    @PostMapping("/editSysRequestPath")
    public JsonResult editSysRequestPath(SysRequestPath sysRequestPath,DataSourceVo dataSourceVo,String urls){
        sysRequestPath.setUrl(urls);
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysRequestPathService.editSysRequestPath(sysRequestPath);
    }
    @PostMapping("/deleteSysRequestPathById")
    public JsonResult deleteSysRequestPathById(Integer id,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysRequestPathService.deleteSysRequestPathById(id);
    }
}
