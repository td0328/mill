package com.myth.system.bean;

import lombok.Data;

@Data
public class SysDict {
    private Integer id;
    private Integer classId;
    private String value;
    private String title;
    private Integer sort;
}
