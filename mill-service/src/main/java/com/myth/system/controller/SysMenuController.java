package com.myth.system.controller;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultCode;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysMenu;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@ResponseBody
@CrossOrigin
public class SysMenuController {
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private SysMenuService sysMenuService;
    @PostMapping("/getAllSysMenu")
    public JsonResult getAllSysMenu(DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysMenuService.getAllSysMenu();
    }
    @PostMapping("/addSysMenu")
    public JsonResult addSysMenu(SysMenu sysMenu,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysMenuService.addSysMenu(sysMenu);
    }
    @PostMapping("/editSysMenu")
    public JsonResult editSysMenu(SysMenu sysMenu,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysMenuService.editSysMenu(sysMenu);
    }
    @PostMapping("/deleteSysMenuByIds")
    public JsonResult deleteSysMenuByIds(Integer[] ids,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysMenuService.deleteSysMenuByIds(ids);
    }

}
