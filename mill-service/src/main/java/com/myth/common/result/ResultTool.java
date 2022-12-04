package com.myth.common.result;


public class ResultTool {
    public static JsonResult success() {
        return new JsonResult(true);
    }

    public static JsonResult success(ResultCode resultEnum) {
        return new JsonResult(true,resultEnum);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(true, data);
    }

    public static JsonResult success(ResultCode resultEnum,Object data){
        return new JsonResult(true,resultEnum,data);
    }
    public static JsonResult success(String msg,Object data){
        return new JsonResult(true,200,msg,data);
    }
    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }
    public static JsonResult fail(String msg) {
        return new JsonResult(false, 999,msg,null);
    }
}
