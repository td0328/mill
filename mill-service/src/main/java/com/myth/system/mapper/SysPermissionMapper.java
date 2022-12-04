package com.myth.system.mapper;

import com.myth.system.bean.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper {
    List<SysPermission> getSysPermissionByMenuId(Integer menuId);
    List<SysPermission> getUserRolesByUserId(Integer userId);
    List<SysPermission> selectListByPath(String requestUrl);
    SysPermission getSysPermissionById(Integer menuId);
    Integer addSysPermission(SysPermission sysPermission);
    Integer editSysPermission(SysPermission sysPermission);
    Integer deleteSysPermissionById(Integer id);

    List<SysPermission> getTreeSysPermissionByMenuId(Integer menuId);
    SysPermission getGetSysPermissionByMenuId(Integer menuId);
    List<SysPermission> getMySysPermissionByUserId(Integer userId);
}
