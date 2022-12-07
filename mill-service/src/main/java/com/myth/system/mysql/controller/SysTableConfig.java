package com.myth.system.mysql.controller;

import com.myth.common.result.JsonResult;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.mysql.service.SysTableService;
import com.myth.system.table.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        dynamicDataSource.createDataSource(dataSourceVo);
        return tableService.getAllSysTableConfigByTableSchema(dataSourceVo.getDataName());
    }
    //配置表标题
    @PostMapping("/mysql/addSysTableConfig")
    public JsonResult addSysTableConfig(String tableSchema,String tableName,String title,DataSourceVo dataSourceVo) {
        dynamicDataSource.createDataSource(dataSourceVo);
        return tableService.addSysTableConfig(tableSchema,tableName,title);
    }
    @PostMapping("/mysql/editSysTableConfigTitle")
    public JsonResult editSysTableConfigTitle(String tableName,String title,DataSourceVo dataSourceVo){
        dynamicDataSource.createDataSource(dataSourceVo);
        return tableService.editSysTableConfigTitle(tableName,title);
    }
    //修改表配置（排序）
    @PostMapping("/mysql/editSysTableConfigSort")
    public JsonResult editSysTableConfigSort(String tableName,String column,String type,String sort,DataSourceVo dataSourceVo) {
        dynamicDataSource.createDataSource(dataSourceVo);
        return tableService.editSysTableConfigSort(tableName,column,type,sort);
    }

    //删除表配置
    @PostMapping("/mysql/deleteSysTableConfigByIds")
    public JsonResult deleteConfig(String[] ids,String tableSchema,DataSourceVo dataSourceVo) {
        dynamicDataSource.createDataSource(dataSourceVo);
        return tableService.deleteSysTableConfigByIds(ids,tableSchema);
    }
    //获取所有列
    @PostMapping("/mysql/getTableColumnConfigByTableName")
    public JsonResult getTableColumnConfigByTableName(String tableName,DataSourceVo dataSourceVo) {
        dynamicDataSource.createDataSource(dataSourceVo);
        return tableService.getTableColumnConfigByTableName(tableName);
    }
    //根据列信息获取可选择的数据类型与数据字典
    @PostMapping("/mysql/getColumnTypeData")
    public JsonResult getColumnTypeData(DataSourceVo dataSourceVo,String tableName,String columnName) {
        dynamicDataSource.createDataSource(dataSourceVo);
        return tableService.getColumnTypeData(tableName,columnName);
    }
    @PostMapping("/mysql/editColumnConfig")
    public JsonResult editColumnConfig(Column column,DataSourceVo dataSourceVo,String columnDateType){
        column.setDataType(columnDateType);
        dynamicDataSource.createDataSource(dataSourceVo);
        return tableService.editColumnConfig(column);
    }
}
