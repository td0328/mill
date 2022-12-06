package com.myth.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myth.common.result.JsonResult;
import com.myth.common.utils.JwtUtils;
import com.myth.system.bean.SysUser;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@ResponseBody
@CrossOrigin
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @PostMapping("/getSysUserByQueryWrapper")
    public Page getSysUserByQueryWrapper(String userName, Integer pageNum, Integer pageSize){
        dynamicDataSource.createDataSource(null);
        QueryWrapper<?> ew = new QueryWrapper<>();
        ew.like("user_name",userName);
        Page<?> page = new Page(pageNum, pageSize);
        return sysUserService.getSysUserByQueryWrapper(page,ew);
    }
    @PostMapping("/getAllSysRoleByUserId")
    public JsonResult getAllSysRoleByUserId(Integer userId){
        dynamicDataSource.createDataSource(null);
        return sysUserService.getAllSysRoleByUserId(userId);
    }
    @PostMapping("/addSysUser")
    public JsonResult addSysUser(SysUser sysUser, Integer[] roleIds, HttpServletRequest httpServletRequest){
        dynamicDataSource.createDataSource(null);
        String myAccount = JwtUtils.getUserAccountByJwtToken(httpServletRequest);
        return sysUserService.addSysUser(sysUser,roleIds,myAccount);
    }
    @PostMapping("/editSysUser")
    public JsonResult editSysUser(SysUser sysUser, Integer[] roleIds, HttpServletRequest httpServletRequest){
        dynamicDataSource.createDataSource(null);
        String myAccount = JwtUtils.getUserAccountByJwtToken(httpServletRequest);
        return sysUserService.editSysUser(sysUser,roleIds,myAccount);
    }
    @PostMapping("/deleteSysUserByIds")
    public JsonResult deleteSysUserByIds(Integer[] ids){
        dynamicDataSource.createDataSource(null);
        return sysUserService.deleteSysUserByIds(ids);
    }
    @PostMapping("/resetPassword")
    public JsonResult resetPassword(SysUser sysUser){
        dynamicDataSource.createDataSource(null);
        return sysUserService.resetPassword(sysUser);
    }
    @PostMapping("/editPassword")
    public JsonResult editPassword(HttpServletRequest httpServletRequest,String oidPassword,String newPassword){
        dynamicDataSource.createDataSource(null);
        String myAccount = JwtUtils.getUserAccountByJwtToken(httpServletRequest);
        return sysUserService.editPassword(myAccount,oidPassword,newPassword);
    }
    @PostMapping("/getMySysUserAndMySysMenuAndMySysPermissionByUserId")
    public JsonResult getMySysUserAndMySysMenuAndMySysPermissionByUserId(HttpServletRequest request){
        dynamicDataSource.createDataSource(null);
        String id = JwtUtils.getUserIdByJwtToken(request);
        return sysUserService.getMySysUserAndMySysMenuAndMySysPermissionByUserId(Integer.parseInt(id));
    }
    @PostMapping("/setEnabled")
    public JsonResult setEnabled(SysUser sysUser){
        dynamicDataSource.createDataSource(null);
        return sysUserService.setEnabled(sysUser);
    }
}
