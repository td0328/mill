package com.myth.system.mapper;

import com.myth.system.bean.SysDictClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDictClassMapper {
    public List<SysDictClass> getAllSysDictClass();
    public Integer insertSysDictClass(SysDictClass sysDictClass);
    public Integer updateSysDictClass(SysDictClass sysDictClass);
    public Integer deleteSysDictClass(Integer id);
    public Integer deleteSysDictByClassId(Integer classId);
}
