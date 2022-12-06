package com.myth.system.controller;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysState;
import com.myth.system.bean.SysStateClass;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysStateClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@ResponseBody
@CrossOrigin
public class SysStateClassController {
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private SysStateClassService sysStateClassService;
    @PostMapping("/getAllSysStateClass")
    public JsonResult getAllSysStateClass(DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysStateClassService.getAllSysStateClass();
    }
    @PostMapping("/addSysStateClass")
    public JsonResult addSysStateClass(SysStateClass sysStateClass,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysStateClassService.addSysStateClass(sysStateClass);
    }
    @PostMapping("/editSysStateClass")
    public JsonResult editSysStateClass(SysStateClass sysStateClass,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysStateClassService.editSysStateClass(sysStateClass);
    }
    @PostMapping("/deleteStateClassByIds")
    public JsonResult deleteStateClassByIds(Integer[] ids,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysStateClassService.deleteStateClassByIds(ids);
    }
    @PostMapping("/getSysStateByClassId")
    public JsonResult getSysStateByClassId(Integer classId,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysStateClassService.getSysStateByClassId(classId);
    }
    @PostMapping("/addSysState")
    public JsonResult addSysState(SysState sysState,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysStateClassService.addSysState(sysState);
    }
    @PostMapping("/editSysState")
    public JsonResult editSysState(SysState sysState,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysStateClassService.editSysState(sysState);
    }
    @PostMapping("/deleteSysStateByIds")
    public JsonResult deleteSysStateByIds(Integer[] ids,Integer classId,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysStateClassService.deleteSysStateByIds(ids,classId);
    }
}
