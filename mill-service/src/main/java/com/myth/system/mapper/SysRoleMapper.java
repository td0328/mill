package com.myth.system.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myth.system.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    SysRole getSysRoleById(Integer id);

    List<SysRole> getAllSysRole();

    Page<SysRole> getSysRoleByQueryWrapper(@Param("page") Page<?> page, @Param(Constants.WRAPPER) QueryWrapper<?> ew);
    Integer addSysRole(SysRole sysRole);

    Integer deleteSysRole(Integer[] ids);
    Integer editSysRole(SysRole sysRole);
}
