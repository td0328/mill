package com.myth.system.mysql.service;

import com.alibaba.fastjson.JSONObject;
import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysDictClass;
import com.myth.system.bean.SysStateClass;
import com.myth.system.bean.SysTableConfig;
import com.myth.system.mysql.bean.SysKeyColumnUsage;
import com.myth.system.mysql.bean.SysTable;
import com.myth.system.mysql.bean.SysTableColumn;
import com.myth.system.table.*;
import com.myth.system.mysql.mapper.SysTableMapper;
import com.myth.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SysTableService {
    @Autowired
    private SysTableMapper tableMapper;

    public JsonResult getAllSysTableConfigByTableSchema(String tableSchema) {
        return ResultTool.success(tableMapper.getAllSysTableConfigByTableSchema(tableSchema));
    }

    public JsonResult addSysTableConfig(String tableSchema, String tableName, String title) {
        //初始化表配置
        Table table = new Table();
        table.setTableSchema(tableSchema);
        table.setTableName(tableName);
        table.setTitle(title);
        table.setSort(new HashMap<>());
        table.setColumns(new ArrayList<>());
        Integer i = tableMapper.addSysTableConfig(tableName, JSONObject.toJSONString(table));
        return ResultTool.success(tableMapper.getAllSysTableConfigByTableSchema(tableSchema));
    }

    public JsonResult editSysTableConfigTitle(String tableName, String title) {
        Table table = getTableByName(tableName);
        table.setTitle(title);
        Integer i = tableMapper.editSysTableConfig(JSONObject.toJSONString(table), tableName);
        return ResultTool.success(tableMapper.getAllSysTableConfigByTableSchema(table.getTableSchema()));
    }

    public JsonResult deleteSysTableConfigByIds(String[] ids, String tableSchema) {
        tableMapper.deleteSysTableConfigByIds(ids);
        return ResultTool.success(tableMapper.getAllSysTableConfigByTableSchema(tableSchema));
    }

    public JsonResult getTableColumnConfigByTableName(String tableName) {
        Table table = getTableByName(tableName);
        List<Column> oidColumnAllConfig = table.getColumns();
        List<Column> newColumnAllConfig = new ArrayList<>();
        List<SysTableColumn> tableColumnList = tableMapper.getTableColumnByTable(table.getTableSchema(), tableName);
        for (SysTableColumn tableColumn : tableColumnList) {
            String columnName = tableColumn.getColumnName();
            SysKeyColumnUsage sysKeyColumnUsage = tableMapper.getKeyColumnUsageByColumn(table.getTableSchema(), tableName, tableColumn.getColumnName());
            Column column = TableConfigAnalysis.initColumn(tableColumn,sysKeyColumnUsage,null);
            for (Column oidColumn:oidColumnAllConfig) {
                if (oidColumn.getColumnName().equals(columnName)) {
                    if (!oidColumn.getDataType().equals(tableColumn.getDataType())){
                        System.out.println(oidColumn.getDataType()+":"+tableColumn.getDataType());
                        //清空列配置
                        column = TableConfigAnalysis.initColumn(tableColumn,sysKeyColumnUsage,new Column());
                        column.setColumnName(columnName);
                    }else{
                        column = TableConfigAnalysis.initColumn(tableColumn,sysKeyColumnUsage,oidColumn);
                    }
                }
            }
            newColumnAllConfig.add(column);
        }
        table.setColumns(newColumnAllConfig);
        String tableJsonStr = JSONObject.toJSONString(table);
        tableMapper.editSysTableConfig(tableJsonStr, tableName);
        return ResultTool.success(table);
    }

    public SysTableConfig getSysTableConfigByName(String tableName) {
        return tableMapper.getSysTableConfigByName(tableName);
    }

    public List<SysTableColumn> getTableColumnByTable(String tableSchema, String tableName) {
        return tableMapper.getTableColumnByTable(tableSchema, tableName);
    }

    public JsonResult editSysTableConfigSort(String tableName, String column, String type, String sort) {
        Table table = getTableByName(tableName);
        HashMap<String,String> sortMap = table.getSort();
        if (type.equals("add")) {
            sortMap.put(column, sort);
        }
        if (type.equals("del")) {
            sortMap.remove(column);
        }
        table.setSort(sortMap);
        tableMapper.editSysTableConfig(JSONObject.toJSONString(table), tableName);
        return ResultTool.success(table);
    }

    public JsonResult getColumnTypeData(String tableName, String columnName) {
        Table table = getTableByName(tableName);
        HashMap<String,Object> map = new HashMap<>();
        List<HashMap<String,String>> columnTypeList = TableConfigAnalysis.getPageDataType(table,columnName);
        map.put("dataTypeList",columnTypeList);
        List<SysDictClass> sysDictClassList = tableMapper.getDictClassAll();
        map.put("dataDictionaryList",sysDictClassList);
        List<SysStateClass> sysStateClassList = tableMapper.getStateClassAll();
        map.put("stateClassList",sysStateClassList);
        //获取数据选择父表的列信息
        Column column = TableConfigAnalysis.getColumnByName(table,columnName);
        List<Column> columnList = new ArrayList<>();
        if(column.getReferencedTableName()!=null&&!column.getReferencedTableName().equals("")){
            Table pTable = getTableByName(column.getReferencedTableName());
            columnList = pTable.getColumns();
        }
        map.put("pColumnList",columnList);
        return ResultTool.success(map);
    }
    public JsonResult editColumnConfig (Column column){
        SysTableConfig sysTableConfig = tableMapper.getSysTableConfigByName(column.getTableName());
        Table table = TableConfigAnalysis.JsonToTable(sysTableConfig.getContent());
        SysTableColumn sysTableColumn = tableMapper.getTableColumnByName(table.getTableSchema(),table.getTableName(),column.getColumnName());
        SysKeyColumnUsage sysKeyColumnUsage = tableMapper.getKeyColumnUsageByColumn(table.getTableSchema(), table.getTableName(), column.getColumnName());
        Column newColumn = TableConfigAnalysis.initColumn(sysTableColumn,sysKeyColumnUsage,null);
        Field[] oidFields  = column.getClass().getDeclaredFields();
        Field[] newFields  = newColumn.getClass().getDeclaredFields();
        for(int i=0;i<oidFields.length;i++){
            try {
                oidFields[i].setAccessible(true);
                newFields[i].setAccessible(true);
                Object value = oidFields[i].get(column);
                if(value!=null&&!value.toString().equals("")){
                    newFields[i].set(newColumn,value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        List<Column> columnList = table.getColumns();
        for(int i=0;i<columnList.size();i++){
            if(columnList.get(i).getColumnName().equals(newColumn.getColumnName())){
                columnList.set(i,newColumn);
                break;
            }
        }
        table.setColumns(columnList);
        String tableJson = JSONObject.toJSONString(table);
        tableMapper.editSysTableConfig(tableJson,table.getTableName());
        return ResultTool.success(newColumn);
    }
    //获取table配置
    public Table getTableByName(String tableName){
        SysTableConfig sysTableConfig = tableMapper.getSysTableConfigByName(tableName);
        Table table = TableConfigAnalysis.JsonToTable(sysTableConfig.getContent());
        return table;
    }
}
