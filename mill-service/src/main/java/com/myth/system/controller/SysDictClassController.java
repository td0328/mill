package com.myth.system.controller;

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
    @GetMapping("/sysDictClass")
    public JsonData getAllSysDictClass(DataSourceVo dataSourceVo){
        try {
            //切换数据源之前先清空
            DynamicDataSource.clearDataSource();
            //切换数据源
            dynamicDataSource.createDataSource(dataSourceVo);
            return sysDictClassService.getAllSysDictClass();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //throw new RuntimeException(e);
            return JsonData.buildFail();
        }
    }
    @PostMapping("/sysDictClass")
    public JsonData saveSysDictClass(SysDictClass sysDictClass){
        if(sysDictClass.getId()==null)
            return sysDictClassService.insertSysDictClass(sysDictClass);
        else
            return sysDictClassService.updateSysDictClass(sysDictClass);
    }
    @DeleteMapping("/sysDictClass")
    public JsonData deleteSysDictClass(@RequestParam Integer[] ids)
    {
        return sysDictClassService.deleteSysDictClass(ids);
    }
}
