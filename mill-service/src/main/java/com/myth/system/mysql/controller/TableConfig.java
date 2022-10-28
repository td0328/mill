package com.myth.system.mysql.controller;

import com.myth.system.mysql.bean.SysTable;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.mysql.service.TableService;
import com.myth.system.table.Column;
import com.myth.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@CrossOrigin
public class TableConfig {

    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private TableService tableService;


    @PostMapping("/mysql/getTableConfig")
    public JsonData getTableConfig(DataSourceVo dataSourceVo) {
        try {
            //切换数据源之前先清空
            DynamicDataSource.clearDataSource();
            //切换数据源
            dynamicDataSource.createDataSource(dataSourceVo);
            List<SysTable> sysTables = tableService.getAllTable(dataSourceVo.getDataName());
            return JsonData.buildSuccess(sysTables);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //throw new RuntimeException(e);
            return JsonData.buildFail();
        }
    }
    //配置表标题
    @PostMapping("/mysql/tableConfig")
    public JsonData sysTableConfig(@RequestParam String tableSchema,
                                   @RequestParam String tableName,
                                   @RequestParam String title,
                                   @RequestParam String type)
    {
        if(type.equals("add"))
            return tableService.insertSysTableConfig(tableSchema,tableName,title);
        else
            return tableService.updateSysTableConfigTitle(tableName,title);
    }
    //修改表配置（排序）
    @PutMapping("/mysql/tableConfig")
    public JsonData updateSysTableConfigSort(@RequestParam String tableName,
                                             @RequestParam String column,
                                             @RequestParam String type,
                                             @RequestParam String sort)
    {
        return tableService.updateSysTableConfigSort(tableName,column,type,sort);
    }

    //删除表配置
    @DeleteMapping("/mysql/tableConfig")
    public JsonData deleteConfig(@RequestParam String[] ids,
                                 @RequestParam String tableSchema)
    {
        return tableService.deleteSysTableConfig(ids,tableSchema);
    }
    //获取所有列
    @PostMapping("/mysql/tableColumn")
    public JsonData getTableColumn(@RequestParam String tableName)
    {
        return tableService.getTableColumnConfigByTable(tableName);
    }
    //根据列信息获取可选择的数据类型与数据字典
    @PostMapping("/mysql/getColumnTypeData")
    public JsonData getColumnTypeData(@RequestParam String tableName,
                                  @RequestParam String columnName)
    {
        return tableService.getColumnTypeData(tableName,columnName);
    }
    @PostMapping("/mysql/saveColumnConfig")
    public JsonData saveColumnConfig(Column column)
    {
        return tableService.saveColumnConfig(column);
    }
}
