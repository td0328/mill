package com.myth.system.mapper;

import com.myth.system.bean.SysState;
import com.myth.system.bean.SysStateClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysStateClassMapper {
    public List<SysStateClass> getAllSysStateClass();
    public Integer insertSysStateClass(SysStateClass sysStateClass);
    public Integer updateSysStateClass(SysStateClass sysStateClass);
    public Integer deleteSysStateClass(Integer id);
    public Integer deleteSysStateByClassId(Integer classId);

    public List<SysState> getAllSysStateByClassId(Integer classId);
    public Integer insertSysState(SysState sysState);
    public Integer updateSysState(SysState sysState);
    public Integer deleteSysState(Integer[] ids);
    public List<SysState> getSysStateByClassIdAndValue(Integer classId,Integer value);
}
