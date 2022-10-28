package com.myth.system.mysql.bean;

import lombok.Data;

@Data
public class SysKeyColumnUsage {
    private String constraintCatalog;
    private String constraintSchema;
    private String constraintName;
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String columnName;
    private Integer ordinalPosition;
    private Integer positionInUniqueConstraint;
    private String referencedTableSchema;
    private String referencedTableName;
    private String referencedColumnName;
}
