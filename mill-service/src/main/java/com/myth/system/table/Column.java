package com.myth.system.table;

import lombok.Data;

@Data
public class Column {
    private String tableName;             //所属表名称
    private String columnName;             //列名称
    private String title;                  //列标题
    private String extra;                  //值为auto_increment时自动增长列
    private Boolean isKey;                 //是否为主键
    private String dataType;               //数据库中的数据类型
    private Long characterMaximumLength;   //数据库最大长度
    private String referencedTableName;    //依赖表名
    private String referencedColumnName;   //依赖列名
    //以上为数据库基础配置
    private String columnType;             //数据填充方式
    private Boolean isShow;                //是否在列表页显示
    private Boolean isQuery;               //是否作为查询条件
    private Boolean isAdd;                 //是否在新增页显示
    private Boolean isEdit;                //是否在编辑页显示
    private Boolean isSee;               //是否在查看页显示

    //字符串类型
    private String regular;                //正则表达式（验证内容用）
    private String tips;                   //验证提示内容
    //整数
    private Integer integerMin;            //最小值
    private Integer integerMax;            //最大值
    //小数
    private Double decimalMin;             //最小值
    private Double decimalMax;             //最大值
    private Integer numericScale;          //小数精度
    //系统状态
    private Integer stateId;               //状态ID
    //数据选择
    private Boolean isMasterShow;           //是否在主表显示
    private String selectorShow;           //选择时显示的列
    private String selectorQuery;          //选择时查询条件
    private String selectorWhere;          //选择范围sql语句
    //下拉选择
    private String selectTableWhere;       //选择范围sql语句
    private Integer selectDictId;          //数据字典ID
    //复选框
    private Integer checkboxDictId;        //数据字典ID
    //单选按钮
    private Integer radioDictId;           //数据字典ID
    //图片
    private Integer imageMin;              //最小张数
    private Integer imageMax;              //最大张数
    //日期
    private Boolean dateStart;             //最小当前时间
    private Boolean dateCurrent;           //当前时间为默认值
    //日期时间
    private Boolean datetimeStart;             //最小当前时间
    private Boolean datetimeCurrent;           //当前时间为默认值
    //日期范围
    private Boolean dateRangeStart;             //最小当前时间
    //日期时间范围
    private Boolean datetimeRangeStart;             //最小当前时间




}
