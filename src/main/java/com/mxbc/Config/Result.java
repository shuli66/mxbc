package com.mxbc.Config;

import java.util.HashMap;
import java.util.Map;
//对接口返回结果进行封装
public class Result {
    public static Map<String,Object> setResult(String code, String msg, Object data){
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("code",code);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }


    public static Map<String,Object> setResultError(String msg){
        return setResult("500",msg,null);
    }

    public static Map<String,Object> setResultError2(String code,String msg){
        return setResult(code,msg,null);
    }

    public static Map<String,Object> setResultOk(Object data){
        return setResult("200","ok",data);
    }

    public static Map<String,Object> setResultSuccess(String msg){
        return setResult("200",msg,null);
    }

    public static Map<String,Object> setResultEorMsg(String msg){
        return setResult("500",msg,null);
    }
}
