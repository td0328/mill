package com.myth.system.mapper;

import com.myth.system.bean.SysMenu;
import com.myth.system.vo.SysMenuVo;
import com.myth.system.vo.TreeSysMenuChildrenVo;
import com.myth.system.vo.TreeSysPermissionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    public List<TreeSysPermissionVo> getTreeSysMenu();
    public List<TreeSysMenuChildrenVo> getTreeSysMenuChildrenByPid(Integer pid);
    public Integer addSysMenu(SysMenu sysMenu);
    public Integer editSysMenu(SysMenu sysMenu);
    public Integer deleteSysMenuByIds(Integer[] ids);
    public Integer deleteSysMenuByPid(Integer[] ids);
    public List<SysMenuVo> getAllSysMenu();
    public List<SysMenuVo> getSysMenuByUserId(Integer userId);
    public SysMenu getSysMenuById(Integer id);
}
