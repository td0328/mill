package com.myth.system.service;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultCode;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysDictClass;
import com.myth.system.bean.SysTableConfig;
import com.myth.system.enums.PageDataType;
import com.myth.system.mapper.SysDictClassMapper;
import com.myth.system.mapper.SysTableConfigMapper;
import com.myth.system.table.Column;
import com.myth.system.table.Table;
import com.myth.system.table.TableConfigAnalysis;
import com.myth.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SysDictClassService {
    @Autowired
    private SysDictClassMapper sysDictClassMapper;
    @Autowired
    private SysTableConfigMapper sysTableConfigMapper;
    public JsonResult getAllSysDictClass(){
        List<SysDictClass> list = sysDictClassMapper.getAllSysDictClass();
        return ResultTool.success(list);
    }
    public JsonResult addSysDictClass(SysDictClass sysDictClass){
        sysDictClassMapper.addSysDictClass(sysDictClass);
        return ResultTool.success(sysDictClassMapper.getAllSysDictClass());
    }
    public JsonResult editSysDictClass(SysDictClass sysDictClass){
        sysDictClassMapper.editSysDictClass(sysDictClass);
        return ResultTool.success(sysDictClassMapper.getAllSysDictClass());
    }
    public JsonResult deleteSysDictClassByIds(Integer[] ids){
        List<SysTableConfig> sysTableConfigList = sysTableConfigMapper.getAllSysTableConfig();
        int i = 0 ;
        for(Integer id:ids){
            boolean is = true;
            for(SysTableConfig sysTableConfig:sysTableConfigList){
                Table table = TableConfigAnalysis.JsonToTable(sysTableConfig.getContent());
                List<Column> columnList = table.getColumns();
                for(Column column:columnList){
                    if(column.getColumnType()!=null){
                        if(column.getColumnType().equals(PageDataType.select.name()))
                            if(Objects.equals(column.getSelectDictId(), id))is = false;
                        if(column.getColumnType().equals(PageDataType.checkbox.name()))
                            if(Objects.equals(column.getCheckboxDictId(), id))is = false;
                        if(column.getColumnType().equals(PageDataType.radio.name()))
                            if(Objects.equals(column.getRadioDictId(), id))is = false;
                    }
                }
            }
            if(is){
                i = i+sysDictClassMapper.deleteSysDictClassById(id);
                sysDictClassMapper.deleteSysDictByClassId(id);
            }
        }
        String msg = "成功删除"+i+"条记录";
        if(i<ids.length){
            msg = msg +"，其余在配置中有引用";
        }
        return ResultTool.success(msg,sysDictClassMapper.getAllSysDictClass());
    }
}
