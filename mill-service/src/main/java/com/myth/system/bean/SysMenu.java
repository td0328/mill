package com.myth.system.bean;

import lombok.Data;

@Data
public class SysMenu {
    private Integer id;
    private Integer pid;
    private String icon;
    private String title;
    private Integer type;
    private String tableName;
    private String pageName;
    private Integer sort;
}
