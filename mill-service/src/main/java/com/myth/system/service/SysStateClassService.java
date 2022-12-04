package com.myth.system.service;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysState;
import com.myth.system.bean.SysStateClass;
import com.myth.system.bean.SysTableConfig;
import com.myth.system.enums.PageDataType;
import com.myth.system.mapper.SysStateClassMapper;
import com.myth.system.mapper.SysTableConfigMapper;
import com.myth.system.table.Column;
import com.myth.system.table.Table;
import com.myth.system.table.TableConfigAnalysis;
import com.myth.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysStateClassService {
    @Autowired
    private SysStateClassMapper sysStateClassMapper;
    @Autowired
    private SysTableConfigMapper sysTableConfigMapper;


    public JsonResult getAllSysStateClass(){
        List<SysStateClass> list = sysStateClassMapper.getAllSysStateClass();
        return ResultTool.success(list);
    }
    public JsonResult addSysStateClass(SysStateClass sysStateClass){
        sysStateClassMapper.addSysStateClass(sysStateClass);
        return ResultTool.success(sysStateClassMapper.getAllSysStateClass());
    }
    public JsonResult editSysStateClass(SysStateClass sysStateClass){
        sysStateClassMapper.editSysStateClass(sysStateClass);
        return ResultTool.success(sysStateClassMapper.getAllSysStateClass());
    }
    public JsonResult deleteStateClassByIds(Integer[] ids){
        List<SysTableConfig> sysTableConfigList = sysTableConfigMapper.getAllSysTableConfig();
        int i = 0 ;
        for(Integer id:ids){
            boolean is = true;
            for(SysTableConfig sysTableConfig:sysTableConfigList){
                Table table = TableConfigAnalysis.JsonToTable(sysTableConfig.getContent());
                List<Column> columnList = table.getColumns();
                for(Column column:columnList){
                    if(column.getColumnType()!=null&&column.getColumnType().equals(PageDataType.state.name())){
                        if(column.getStateId()==id)is = false;
                    }
                }
            }
            if(is){
                i = i+sysStateClassMapper.deleteSysStateClassById(id);
                sysStateClassMapper.deleteSysStateByClassId(id);
            }
        }
        String msg = "成功删除"+i+"条记录";
        if(i<ids.length){
            msg = msg +"，其余在配置中有引用";
        }
        return ResultTool.success(msg,sysStateClassMapper.getAllSysStateClass());
    }
    public JsonResult getSysStateByClassId(Integer classId){
        return ResultTool.success(sysStateClassMapper.getAllSysStateByClassId(classId));
    }
    public JsonResult addSysState(SysState sysState){
        List<SysState> sysStateList = sysStateClassMapper.getSysStateByClassIdAndValue(sysState.getClassId(),sysState.getValue());
        if(sysStateList.size()>0){
            return ResultTool.fail();
        }else{
            sysStateClassMapper.addSysState(sysState);
            return ResultTool.success(sysStateClassMapper.getAllSysStateByClassId(sysState.getClassId()));
        }
    }
    public JsonResult editSysState(SysState sysState){
        sysStateClassMapper.editSysState(sysState);
        return ResultTool.success(sysStateClassMapper.getAllSysStateByClassId(sysState.getClassId()));
    }
    public JsonResult deleteSysStateByIds(Integer[] ids,Integer classId){
        Integer i = sysStateClassMapper.deleteSysStateByIds(ids);
        String msg = "成功删除"+i+"条数据";
        return ResultTool.success(sysStateClassMapper.getAllSysStateByClassId(classId));
    }
}
