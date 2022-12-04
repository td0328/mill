package com.myth.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultCode;
import com.myth.common.result.ResultTool;
import com.myth.common.utils.JwtUtils;
import com.myth.system.bean.SysDictClass;
import com.myth.system.bean.SysMenu;
import com.myth.system.dataSource.DataSourceVo;
import com.myth.system.dataSource.DynamicDataSource;
import com.myth.system.service.SysDictClassService;
import com.myth.system.service.SysMenuService;
import com.myth.system.service.SysUserService;
import com.myth.system.vo.SysMenuVo;
import com.myth.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@ResponseBody
@CrossOrigin
public class SysMenuController {
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private SysMenuService sysMenuService;
    @PostMapping("/getAllSysMenu")
    public JsonResult getAllSysMenu(DataSourceVo dataSourceVo){
        try {
            //切换数据源之前先清空
            DynamicDataSource.clearDataSource();
            //切换数据源
            dynamicDataSource.createDataSource(dataSourceVo);
            return sysMenuService.getAllSysMenu();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //throw new RuntimeException(e);
            return ResultTool.fail(ResultCode.COMMON_FAIL);
        }
    }
    @PostMapping("/addSysMenu")
    public JsonResult addSysMenu(SysMenu sysMenu){
        return sysMenuService.addSysMenu(sysMenu);
    }
    @PostMapping("/editSysMenu")
    public JsonResult editSysMenu(SysMenu sysMenu){
        return sysMenuService.editSysMenu(sysMenu);
    }
    @PostMapping("/deleteSysMenuByIds")
    public JsonResult deleteSysMenuByIds(@RequestParam Integer[] ids)
    {
        return sysMenuService.deleteSysMenuByIds(ids);
    }

}
