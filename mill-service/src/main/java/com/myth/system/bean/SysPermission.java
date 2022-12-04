package com.myth.system.bean;

import lombok.Data;

@Data
public class SysPermission {
    private Integer id;
    private Integer menuId;
    private String permissionCode;
    private String permissionName;
}
