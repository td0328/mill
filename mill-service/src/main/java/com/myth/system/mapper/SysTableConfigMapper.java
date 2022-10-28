package com.myth.system.mapper;

import com.myth.system.bean.SysTableConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysTableConfigMapper {
    public List<SysTableConfig> getAllSysTableConfig();
}
