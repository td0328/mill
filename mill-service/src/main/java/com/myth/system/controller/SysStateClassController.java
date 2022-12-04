package com.myth.system.controller;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysState;
import com.myth.system.bean.SysStateClass;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysStateClassService;
import com.myth.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
        try {
            //切换数据源之前先清空
            DynamicDataSource.clearDataSource();
            //切换数据源
            dynamicDataSource.createDataSource(dataSourceVo);
            return sysStateClassService.getAllSysStateClass();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //throw new RuntimeException(e);
            return ResultTool.fail();
        }
    }
    @PostMapping("/addSysStateClass")
    public JsonResult addSysStateClass(SysStateClass sysStateClass){
        return sysStateClassService.addSysStateClass(sysStateClass);
    }
    @PostMapping("/editSysStateClass")
    public JsonResult editSysStateClass(SysStateClass sysStateClass){
        return sysStateClassService.editSysStateClass(sysStateClass);
    }
    @PostMapping("/deleteStateClassByIds")
    public JsonResult deleteStateClassByIds(@RequestParam Integer[] ids){
        return sysStateClassService.deleteStateClassByIds(ids);
    }
    @PostMapping("/getSysStateByClassId")
    public JsonResult getSysStateByClassId(@RequestParam Integer classId){
        return sysStateClassService.getSysStateByClassId(classId);
    }
    @PostMapping("/addSysState")
    public JsonResult addSysState(SysState sysState){
        return sysStateClassService.addSysState(sysState);
    }
    @PostMapping("/editSysState")
    public JsonResult editSysState(SysState sysState){
        return sysStateClassService.editSysState(sysState);
    }
    @PostMapping("/deleteSysStateByIds")
    public JsonResult deleteSysStateByIds(@RequestParam Integer[] ids,
                                   @RequestParam Integer classId)
    {
        return sysStateClassService.deleteSysStateByIds(ids,classId);
    }
}
