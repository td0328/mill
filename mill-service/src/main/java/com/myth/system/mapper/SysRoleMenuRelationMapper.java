package com.myth.system.mapper;

import com.myth.system.bean.SysRoleMenuRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysRoleMenuRelationMapper {

    Integer addSysRoleMenuRelation(SysRoleMenuRelation sysRoleMenuRelation);
    Integer deleteSysRoleMenuRelation(Integer[] roleIds);
    List<SysRoleMenuRelation> getSysRoleMenuRelationByRoleId(Integer roleId);
    Integer deleteSysRoleMenuRelationById(Integer id);
    List<SysRoleMenuRelation> getSysRoleMenuRelationByRoleIdAndMenuId(Integer roleId,Integer menuId);
}
