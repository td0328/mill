package com.myth.system.mysql.mapper;

import com.myth.system.bean.SysDictClass;
import com.myth.system.bean.SysStateClass;
import com.myth.system.bean.SysTableConfig;
import com.myth.system.mysql.bean.SysKeyColumnUsage;
import com.myth.system.mysql.bean.SysTable;
import com.myth.system.mysql.bean.SysTableColumn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TableMapper {
    public List<SysTable> getAllTable(String tableSchema);
    public Integer insertSysTableConfig(String tableName,String content);
    public Integer updateSysTableConfig(String content, String tableName);
    public Integer deleteSysTableConfig(String[] ids);
    public List<SysTableColumn> getTableColumnByTable(String tableSchema, String tableName);
    public SysTableConfig getSysTableConfigByName(String tableName);
    public SysKeyColumnUsage getKeyColumnUsageByColumn(String tableSchema, String tableName, String columnName);
    public SysTableColumn getTableColumnByName(String tableSchema, String tableName, String columnName);
    public List<SysDictClass> getDictClassAll();
    public List<SysStateClass> getStateClassAll();
}
