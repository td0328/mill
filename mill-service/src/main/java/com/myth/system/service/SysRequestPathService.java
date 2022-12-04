package com.myth.system.service;

import com.myth.common.result.JsonResult;
import com.myth.common.result.ResultTool;
import com.myth.system.bean.SysRequestPath;
import com.myth.system.mapper.SysRequestPathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRequestPathService {
    @Autowired
    private SysRequestPathMapper sysRequestPathMapper;
    public JsonResult getSysRequestPathByPermissionId(Integer permissionId){
        return ResultTool.success(sysRequestPathMapper.getSysRequestPathByPermissionId(permissionId));
    }
    public JsonResult addSysRequestPath(SysRequestPath sysRequestPath){
        sysRequestPathMapper.addSysRequestPath(sysRequestPath);
        return ResultTool.success(sysRequestPathMapper.getSysRequestPathByPermissionId(sysRequestPath.getPermissionId()));
    }
    public JsonResult editSysRequestPath(SysRequestPath sysRequestPath){
        sysRequestPathMapper.editSysRequestPath(sysRequestPath);
        return ResultTool.success(sysRequestPathMapper.getSysRequestPathByPermissionId(sysRequestPath.getPermissionId()));
    }
    public JsonResult deleteSysRequestPathById(Integer id){
        SysRequestPath sysRequestPath = sysRequestPathMapper.getSysRequestPathById(id);
        sysRequestPathMapper.deleteSysRequestPathById(sysRequestPath.getId());
        return ResultTool.success(sysRequestPathMapper.getSysRequestPathByPermissionId(sysRequestPath.getPermissionId()));
    }
}
