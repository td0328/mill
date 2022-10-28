package com.myth.system.table;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class Table {
    private String tableSchema;
    private String tableName;
    private String title;
    private HashMap<String,String> sort;
    private List<Column> columns;
}
