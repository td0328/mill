package com.myth.system.mysql.controller;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.mysql.bean.SysTable;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.mysql.service.SysTableService;
import com.myth.system.table.Column;
import com.myth.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@CrossOrigin
public class SysTableConfig {

    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private SysTableService tableService;


    @PostMapping("/mysql/getAllSysTableConfigByTableSchema")
    public JsonResult getAllSysTableConfigByTableSchema(DataSourceVo dataSourceVo) {
        try {
            //切换数据源之前先清空
            DynamicDataSource.clearDataSource();
            //切换数据源
            dynamicDataSource.createDataSource(dataSourceVo);
            return tableService.getAllSysTableConfigByTableSchema(dataSourceVo.getDataName());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //throw new RuntimeException(e);
            return ResultTool.fail();
        }
    }
    //配置表标题
    @PostMapping("/mysql/addSysTableConfig")
    public JsonResult addSysTableConfig(@RequestParam String tableSchema,
                                   @RequestParam String tableName,
                                   @RequestParam String title)
    {
        return tableService.addSysTableConfig(tableSchema,tableName,title);
    }
    @PostMapping("/mysql/editSysTableConfigTitle")
    public JsonResult editSysTableConfigTitle(@RequestParam String tableName,
                                        @RequestParam String title)
    {
        return tableService.editSysTableConfigTitle(tableName,title);
    }
    //修改表配置（排序）
    @PostMapping("/mysql/editSysTableConfigSort")
    public JsonResult editSysTableConfigSort(@RequestParam String tableName,
                                             @RequestParam String column,
                                             @RequestParam String type,
                                             @RequestParam String sort)
    {
        return tableService.editSysTableConfigSort(tableName,column,type,sort);
    }

    //删除表配置
    @PostMapping("/mysql/deleteSysTableConfigByIds")
    public JsonResult deleteConfig(@RequestParam String[] ids,
                                 @RequestParam String tableSchema)
    {
        return tableService.deleteSysTableConfigByIds(ids,tableSchema);
    }
    //获取所有列
    @PostMapping("/mysql/getTableColumnConfigByTableName")
    public JsonResult getTableColumnConfigByTableName(@RequestParam String tableName)
    {
        return tableService.getTableColumnConfigByTableName(tableName);
    }
    //根据列信息获取可选择的数据类型与数据字典
    @PostMapping("/mysql/getColumnTypeData")
    public JsonResult getColumnTypeData(@RequestParam String tableName,
                                  @RequestParam String columnName)
    {
        return tableService.getColumnTypeData(tableName,columnName);
    }
    @PostMapping("/mysql/editColumnConfig")
    public JsonResult editColumnConfig(Column column)
    {
        return tableService.editColumnConfig(column);
    }
}
