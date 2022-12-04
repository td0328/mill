package com.myth.system.mapper;

import com.myth.system.bean.SysState;
import com.myth.system.bean.SysStateClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysStateClassMapper {
    public List<SysStateClass> getAllSysStateClass();
    public Integer addSysStateClass(SysStateClass sysStateClass);
    public Integer editSysStateClass(SysStateClass sysStateClass);
    public Integer deleteSysStateClassById(Integer id);
    public Integer deleteSysStateByClassId(Integer classId);

    public List<SysState> getAllSysStateByClassId(Integer classId);
    public Integer addSysState(SysState sysState);
    public Integer editSysState(SysState sysState);
    public Integer deleteSysStateByIds(Integer[] ids);
    public List<SysState> getSysStateByClassIdAndValue(Integer classId,Integer value);
}
