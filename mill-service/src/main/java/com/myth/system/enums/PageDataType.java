package com.myth.system.enums;

public enum PageDataType {
    integer("整数",new String[]{"int","tinyint","smallint","mediumint","integer","bigint"}),
    decimal("小数",new String[]{"float","double","decimal"}),
    string("字符串",new String[]{"char","varchar","tinyblob","tinytext"}),
    selector("数据选择",new String[]{"varchar","int"}),
    select("下拉选择",new String[]{"tinyint","varchar"}),
    checkbox("复选框",new String[]{"json"}),
    radio("单选按钮",new String[]{"varchar","int"}),
    textarea("文本框",new String[]{"blob","text","mediumblob","mediumtext","longblob","longtext"}),
    switched("开关",new String[]{"char"}),
    editor("富文本框",new String[]{"longtext"}),
    file("文件",new String[]{"json"}),
    image("图片",new String[]{"json"}),
    region("地区",new String[]{"json"}),
    date("日期",new String[]{"date"}),
    state("系统状态",new String[]{"tinyint"}),
    time("时间",new String[]{"time"}),
    year("年份",new String[]{"year"}),
    datetime("日期时间",new String[]{"datetime","timestamp"}),
    dateRange("日期范围",new String[]{"json"}),
    datetimeRange("日期时间范围",new String[]{"json"});
    private String text;
    private String[] dataType;
    PageDataType(String text,String[] dataType){
        this.text = text;
        this.dataType = dataType;
    }
    public String getText() {
        return text;
    }
    public String[] getDataType() {
        return dataType;
    }
}
