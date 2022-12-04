package com.myth.system.table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myth.system.mysql.bean.SysKeyColumnUsage;
import com.myth.system.mysql.bean.SysTableColumn;
import com.myth.system.enums.PageDataType;
import com.myth.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 解析表配置类
 * @author xuqiang
 */
public class TableConfigAnalysis{
    /**
     * 将table配置Json字符串转换成table对象
     * @param tableJson table配置字符串
     * @return table对象
     */
    public static Table JsonToTable(String tableJson){
        JSONObject tableConfig = JSONObject.parseObject(tableJson);
        Table table = new Table();
        table.setTableSchema(tableConfig.getString("tableSchema"));
        table.setTableName(tableConfig.getString("tableName"));
        table.setTitle(tableConfig.getString("title"));
        JSONObject sortJson = tableConfig.getJSONObject("sort");
        table.setSort(Utils.changeToMap(sortJson));
        JSONArray columnsJson = tableConfig.getJSONArray("columns");
        List<Column> columns = new ArrayList<>();
        for (Object o:columnsJson){
            JSONObject columnJson = (JSONObject)o;
            Column column = new Column();
            column.setTableName(columnJson.getString("tableName"));
            column.setColumnName(columnJson.getString("columnName"));
            column.setTitle(columnJson.getString("title"));
            column.setExtra(columnJson.getString("extra"));
            column.setIsKey(columnJson.getBoolean("isKey"));
            column.setDataType(columnJson.getString("dataType"));
            column.setReferencedTableName(columnJson.getString("referencedTableName"));
            column.setReferencedColumnName(columnJson.getString("referencedColumnName"));
            column.setColumnType(columnJson.getString("columnType"));
            column.setIsShow(columnJson.getBoolean("isShow"));
            column.setIsQuery(columnJson.getBoolean("isQuery"));
            column.setIsAdd(columnJson.getBoolean("isAdd"));
            column.setIsEdit(columnJson.getBoolean("isEdit"));
            column.setIsSee(columnJson.getBoolean("isSee"));
            column.setRegular(columnJson.getString("regular"));
            column.setTips(columnJson.getString("tips"));
            column.setIntegerMin(columnJson.getInteger("integerMin"));
            column.setIntegerMax(columnJson.getInteger("integerMax"));
            column.setDecimalMin(columnJson.getDouble("decimalMin"));
            column.setDecimalMax(columnJson.getDouble("decimalMax"));
            column.setNumericScale(columnJson.getInteger("numericScale"));
            column.setStateId(columnJson.getInteger("stateId"));
            column.setIsMasterShow(columnJson.getBoolean("isMasterShow"));
            column.setSelectorShow(columnJson.getString("selectorShow"));
            column.setSelectorQuery(columnJson.getString("selectorQuery"));
            column.setSelectorWhere(columnJson.getString("selectorWhere"));
            column.setSelectTableWhere(columnJson.getString("selectTableWhere"));
            column.setSelectDictId(columnJson.getInteger("selectDictId"));
            column.setCheckboxDictId(columnJson.getInteger("checkboxDictId"));
            column.setRadioDictId(columnJson.getInteger("radioDictId"));
            column.setImageMin(columnJson.getInteger("imageMin"));
            column.setImageMax(columnJson.getInteger("imageMax"));
            column.setDateStart(columnJson.getBoolean("dateStart"));
            column.setDateCurrent(columnJson.getBoolean("dateCurrent"));
            column.setDatetimeStart(columnJson.getBoolean("datetimeStart"));
            column.setDatetimeCurrent(columnJson.getBoolean("datetimeCurrent"));
            column.setDateRangeStart(columnJson.getBoolean("dateRangeStart"));
            column.setDatetimeRangeStart(columnJson.getBoolean("datetimeRangeStart"));
            columns.add(column);
        }
        table.setColumns(columns);
        return table;
    }
    /**
     * 初始化列配置
     * @param sysTableColumn table信息
     * @param sysKeyColumnUsage 外键信息
     * @return Column 对象
     */
    public static Column initColumn(SysTableColumn sysTableColumn, SysKeyColumnUsage sysKeyColumnUsage, Column column){
        if(column==null)column = new Column();
        //设置列在数据库中的数据类型
        column.setDataType(sysTableColumn.getDataType());
        column.setTableName(sysTableColumn.getTableName());
        column.setColumnName(sysTableColumn.getColumnName());
        column.setNumericScale(sysTableColumn.getNumericScale());
        //是非为主键
        boolean isKey = sysTableColumn.getColumnKey().equals("PRI");
        column.setIsKey(isKey);
        //值为auto_increment时自动增长列
        column.setExtra(sysTableColumn.getExtra());
        //最大长度
        column.setCharacterMaximumLength(sysTableColumn.getCharacterMaximumLength());
        //设置列引用
        if (sysKeyColumnUsage != null) {
            column.setReferencedTableName(sysKeyColumnUsage.getReferencedTableName());
            column.setReferencedColumnName(sysKeyColumnUsage.getReferencedColumnName());
        }
        return column;
    }
    /**
     * 通过table对象与列名获取列对象
     * @param table  table对象
     * @param columnName 列名称
     * @return 列对象
     */
    public static Column getColumnByName(Table table,String columnName){
        List<Column> columns = table.getColumns();
        for(Column c:columns){
            if(c.getColumnName().equals(columnName))return c;
        }
        return null;
    }
    /**
     * 通过table对象与列名获取可选择的前端数据类型
     * @param table  table对象
     * @param columnName 列名称
     * @return 列对象
     */
    public static List<HashMap<String,String>> getPageDataType(Table table,String columnName){
        List<HashMap<String,String>> list = new ArrayList<>();
        Column column = getColumnByName(table,columnName);
        assert column != null;
        String dataType = column.getDataType();
        PageDataType[] values = PageDataType.values();
        for (PageDataType pageDataType:values) {
            String val = pageDataType.toString();
            String text = pageDataType.getText();
            String[] types = pageDataType.getDataType();
            for(String s:types){
                if(s.equals(dataType)){
                    if(val.equals("selector")&&(column.getReferencedTableName()==null||column.getReferencedTableName().equals("")))continue;
                    if(!val.equals("selector")&&(column.getReferencedTableName()!=null&&!column.getReferencedTableName().equals("")))continue;
                    HashMap<String,String> map = new HashMap<>();
                    map.put("value",val);
                    map.put("text",text);
                    list.add(map);
                }
            }
        }
        return list;
    }
}
