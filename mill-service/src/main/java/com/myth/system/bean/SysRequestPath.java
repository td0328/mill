package com.myth.system.bean;

import lombok.Data;

@Data
public class SysRequestPath {
    private Integer id;
    private Integer permissionId;
    private String url;
    private String description;
}

