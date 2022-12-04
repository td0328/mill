package com.myth.system.vo;

import com.myth.system.bean.SysPermission;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuChildrenVo {
    private Integer id;
    private Integer pid;
    private String icon;
    private String title;
    private Integer type;
    private String tableName;
    private String pageName;
    private Integer sort;
    private Integer userId;
    // 子分类
    private List<SysPermission> children;
}
