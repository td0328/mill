package com.myth.system.controller;

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
    @GetMapping("/sysStateClass")
    public JsonData getAllSysStateClass(DataSourceVo dataSourceVo){
        try {
            //切换数据源之前先清空
            DynamicDataSource.clearDataSource();
            //切换数据源
            dynamicDataSource.createDataSource(dataSourceVo);
            return sysStateClassService.getAllSysStateClass();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //throw new RuntimeException(e);
            return JsonData.buildFail();
        }
    }
    @PostMapping("/sysStateClass")
    public JsonData saveStateClass(SysStateClass sysStateClass){
        if(sysStateClass.getId()==null)
            return sysStateClassService.insertSysStateClass(sysStateClass);
        else
            return sysStateClassService.updateSysStateClass(sysStateClass);
    }
    @DeleteMapping("/sysStateClass")
    public JsonData deleteStateClass(@RequestParam Integer[] ids){
        return sysStateClassService.deleteStateClass(ids);
    }
    @GetMapping("/sysState")
    public JsonData getSysStateByClassId(@RequestParam Integer classId){
        return sysStateClassService.getSysStateByClassId(classId);
    }
    @PostMapping("/sysState")
    public JsonData saveSysState(SysState sysState){
        if(sysState.getId()==null)
            return sysStateClassService.insertSysState(sysState);
        else
            return sysStateClassService.updateSysState(sysState);
    }
    @DeleteMapping("/sysState")
    public JsonData deleteSysState(@RequestParam Integer[] ids,
                                   @RequestParam Integer classId)
    {
        return sysStateClassService.deleteSysState(ids,classId);
    }
}
