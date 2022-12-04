package com.myth.system.controller;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultCode;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysDictClass;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysDictClassService;
import com.myth.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@ResponseBody
@CrossOrigin
public class SysDictClassController {
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private SysDictClassService sysDictClassService;
    @PostMapping("/getAllSysDictClass")
    public JsonResult getAllSysDictClass(DataSourceVo dataSourceVo){
        try {
            //切换数据源之前先清空
            DynamicDataSource.clearDataSource();
            //切换数据源
            dynamicDataSource.createDataSource(dataSourceVo);
            return sysDictClassService.getAllSysDictClass();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //throw new RuntimeException(e);
            return ResultTool.fail(ResultCode.COMMON_FAIL);
        }
    }
    @PostMapping("/addSysDictClass")
    public JsonResult addSysDictClass(SysDictClass sysDictClass){
        return sysDictClassService.addSysDictClass(sysDictClass);
    }
    @PostMapping("/editSysDictClass")
    public JsonResult editSysDictClass(SysDictClass sysDictClass){
        return sysDictClassService.editSysDictClass(sysDictClass);
    }
    @PostMapping("/deleteSysDictClassByIds")
    public JsonResult deleteSysDictClassByIds(@RequestParam Integer[] ids)
    {
        return sysDictClassService.deleteSysDictClassByIds(ids);
    }
}
