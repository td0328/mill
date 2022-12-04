package com.myth.system.mapper;

import com.myth.system.bean.SysDictClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDictClassMapper {
    public List<SysDictClass> getAllSysDictClass();
    public Integer addSysDictClass(SysDictClass sysDictClass);
    public Integer editSysDictClass(SysDictClass sysDictClass);
    public Integer deleteSysDictClassById(Integer id);
    public Integer deleteSysDictByClassId(Integer classId);
}
