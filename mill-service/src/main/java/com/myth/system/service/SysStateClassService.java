package com.myth.system.service;

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


    public JsonData getAllSysStateClass(){
        List<SysStateClass> list = sysStateClassMapper.getAllSysStateClass();
        return JsonData.buildSuccess(list);
    }
    public JsonData insertSysStateClass(SysStateClass sysStateClass){
        sysStateClassMapper.insertSysStateClass(sysStateClass);
        return JsonData.buildSuccess(sysStateClassMapper.getAllSysStateClass());
    }
    public JsonData updateSysStateClass(SysStateClass sysStateClass){
        sysStateClassMapper.updateSysStateClass(sysStateClass);
        return JsonData.buildSuccess(sysStateClassMapper.getAllSysStateClass());
    }
    public JsonData deleteStateClass(Integer[] ids){
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
                i = i+sysStateClassMapper.deleteSysStateClass(id);
                sysStateClassMapper.deleteSysStateByClassId(id);
            }
        }
        String msg = "成功删除"+i+"条记录";
        if(i<ids.length){
            msg = msg +"，其余在配置中有引用";
        }
        return JsonData.buildSuccess(msg,sysStateClassMapper.getAllSysStateClass());
    }
    public JsonData getSysStateByClassId(Integer classId){
        return JsonData.buildSuccess(sysStateClassMapper.getAllSysStateByClassId(classId));
    }
    public JsonData insertSysState(SysState sysState){
        List<SysState> sysStateList = sysStateClassMapper.getSysStateByClassIdAndValue(sysState.getClassId(),sysState.getValue());
        if(sysStateList.size()>0){
            return JsonData.buildFail("该值已存在");
        }else{
            sysStateClassMapper.insertSysState(sysState);
            return JsonData.buildSuccess(sysStateClassMapper.getAllSysStateByClassId(sysState.getClassId()));
        }
    }
    public JsonData updateSysState(SysState sysState){
        sysStateClassMapper.updateSysState(sysState);
        return JsonData.buildSuccess(sysStateClassMapper.getAllSysStateByClassId(sysState.getClassId()));
    }
    public JsonData deleteSysState(Integer[] ids,Integer classId){
        Integer i = sysStateClassMapper.deleteSysState(ids);
        String msg = "成功删除"+i+"条数据";
        return JsonData.buildSuccess(sysStateClassMapper.getAllSysStateByClassId(classId));
    }
}
