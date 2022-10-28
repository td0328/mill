package com.myth.utils;

// 返回统一的json数据格式
public class JsonData {
    private Integer code;   // 表示请求成功或者失败的响应码
    private String msg;     // 请求成功或者失败的信息描述
    private Object data;    // 响应的具体数据
    private Boolean result; // 请求是否成功

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public JsonData(Integer code, String msg, Object data, Boolean result) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.result = result;
    }

    public JsonData() {
    }

    // 需要向外部暴露响应成功以及失败的信息
    // 响应成功
    public static JsonData buildSuccess(Object data){
        return new JsonData(200,"响应成功",data,true);
    }
    public static JsonData buildSuccess(){
        return new JsonData(200,"响应成功",null,true);
    }
    public static JsonData buildSuccess(String msg){
        return new JsonData(200,msg,null,true);
    }
    public static JsonData buildSuccess(String msg,Object data){
        return new JsonData(200,msg,data,true);
    }
    // 响应失败
    public static JsonData buildFail(){
        return new JsonData(-100,"响应失败",null,false);
    }
    public static JsonData buildFail(String msg,Object data){
        return new JsonData(-100,msg,data,false);
    }
    public static JsonData buildFail(String msg){
        return new JsonData(-100,msg,null,false);
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
