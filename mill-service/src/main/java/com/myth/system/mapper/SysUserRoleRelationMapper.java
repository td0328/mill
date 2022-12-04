package com.myth.system.mapper;

import com.myth.system.bean.SysUserRoleRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysUserRoleRelationMapper {

    List<SysUserRoleRelation> getSysUserRoleRelationByRoleId(Integer roleId);
    List<SysUserRoleRelation> getSysUserRoleRelationByUserId(Integer userId);
    Integer addSysUserRoleRelation(SysUserRoleRelation sysUserRoleRelation);
    Integer deleteSysUserRoleRelationById(Integer id);
    List<SysUserRoleRelation> getSysUserRoleRelationByUserIdAndRoleId(Integer userId,Integer roleId);
    Integer deleteSysUserRoleRelationByUserIds(Integer[] userIds);
}
