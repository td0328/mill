package com.myth.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myth.system.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectOne(String account);
    void update(SysUser sysUser, QueryWrapper<SysUser> ew);
    SysUser getSysUserById(Integer id);

    Page<SysUser> getSysUserByQueryWrapper(@Param("page") Page<?> page, @Param(Constants.WRAPPER) QueryWrapper<?> ew);

    Integer addSysUser(SysUser SysUser);
    List<SysUser> getSysUserByIdAndAccount(SysUser SysUser);
    Integer editSysUser(SysUser SysUser);
    Integer deleteSysUserByIds(Integer[] ids);
    Integer resetPassword(SysUser SysUser);
    Integer setEnabled(SysUser SysUser);
}
