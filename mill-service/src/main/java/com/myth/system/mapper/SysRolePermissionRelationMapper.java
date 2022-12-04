package com.myth.system.mapper;

import com.myth.system.bean.SysRolePermissionRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRolePermissionRelationMapper {

    Integer addSysRolePermissionRelation(SysRolePermissionRelation sysRolePermissionRelation);
    Integer deleteSysRolePermissionRelation(Integer[] roleIds);
    List<SysRolePermissionRelation> getSysRolePermissionRelationByRoleId(Integer roleId);

    List<SysRolePermissionRelation> getSysRolePermissionRelationByRoleIdAndPermissionId(Integer roleId,Integer permissionId);
    Integer deleteSysRolePermissionRelationById(Integer id);

}
