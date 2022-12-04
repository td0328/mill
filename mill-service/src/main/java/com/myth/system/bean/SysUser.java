package com.myth.system.bean;

import lombok.Data;

import java.util.Date;
@Data
public class SysUser {
    private Integer id;
    private String account;
    private String userName;
    private String password;
    private Date lastLoginTime;
    private Boolean enabled;
    private Boolean notExpired;
    private Boolean accountNotLocked;
    private Boolean credentialsNotExpired;
    private Date createTime;
    private Date UpdateTime;
    private Integer createUser;
    private Integer UpdateUser;

}
