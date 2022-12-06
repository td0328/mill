package com.myth.system.controller;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultCode;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysDictClass;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysDictClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysDictClassService.getAllSysDictClass();
    }
    @PostMapping("/addSysDictClass")
    public JsonResult addSysDictClass(SysDictClass sysDictClass,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysDictClassService.addSysDictClass(sysDictClass);
    }
    @PostMapping("/editSysDictClass")
    public JsonResult editSysDictClass(SysDictClass sysDictClass,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysDictClassService.editSysDictClass(sysDictClass);
    }
    @PostMapping("/deleteSysDictClassByIds")
    public JsonResult deleteSysDictClassByIds(Integer[] ids,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return sysDictClassService.deleteSysDictClassByIds(ids);
    }
}
