package com.myth.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myth.common.result.JsonResult;
import com.myth.system.bean.SysRole;
import com.myth.system.service.SysMenuService;
import com.myth.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@CrossOrigin
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/getSysRoleByQueryWrapper")
    public Page getSysRoleByQueryWrapper(String roleName,Integer pageNum,Integer pageSize){
        QueryWrapper<?> ew = new QueryWrapper<>();
        ew.like("role_name",roleName);
        Page<?> page = new Page(pageNum, pageSize);
        return sysRoleService.getSysRoleByQueryWrapper(page,ew);
    }
    @PostMapping("/getTreeSysMenu")
    public JsonResult getTreeSysMenu(Integer id){
        return sysMenuService.getTreeSysMenu(id);
    }
    @PostMapping("/addSysRole")
    public JsonResult addSysRole(SysRole sysRole,String menuIds[]){
        return sysRoleService.addSysRole(sysRole,menuIds);
    }
    @PostMapping("/deleteSysRoleByIds")
    public JsonResult deleteSysRoleByIds(Integer[] ids)
    {
        return sysRoleService.deleteSysRole(ids);
    }
    @PostMapping("/editSysRole")
    public JsonResult editSysRole(SysRole sysRole,String menuIds[]){
        return sysRoleService.editSysRole(sysRole,menuIds);
    }
}
