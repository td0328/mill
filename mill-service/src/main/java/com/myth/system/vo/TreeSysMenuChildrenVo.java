package com.myth.system.vo;

import lombok.Data;

import java.util.List;

@Data
public class TreeSysMenuChildrenVo {
    private String id;
    private String label;
    private String className;
    // 子分类
    private List<TreeSysPermissionVo> children;
}
