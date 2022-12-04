package com.myth.system.mapper;

import com.myth.system.bean.SysPermission;
import com.myth.system.bean.SysRequestPath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRequestPathMapper {
    public List<SysRequestPath> getSysRequestPathByPermissionId(Integer permissionId);
    public SysRequestPath getSysRequestPathById(Integer id);
    public Integer addSysRequestPath(SysRequestPath sysRequestPath);
    public Integer editSysRequestPath(SysRequestPath sysRequestPath);
    public Integer deleteSysRequestPathById(Integer id);
    public Integer deleteSysRequestPathByPermissionId(Integer permissionId);
}
