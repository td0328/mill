package com.myth.system.mysql.bean;

import lombok.Data;

import java.util.Date;

@Data
public class SysTable {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String engine;
    private Long version;
    private String rowFormat;
    private Long tableRows;
    private Long avgRowLength;
    private Long dataLength;
    private Long maxDataLength;
    private Long indexLength;
    private Long dataFree;
    private Long autoIncrement;
    private Date createTime;
    private Date updateTime;
    private Date checkTime;
    private String tableCollation;
    private Long checksum;
    private String createOptions;
    private String tableComment;
    //表配置信息
    private String content;

}
