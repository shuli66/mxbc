package com.mxbc.basedao;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
    public Map<String,Object> setResult(String code, String msg, Object data){
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("code",code);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }

    public Map<String,Object> setResultError(String msg){
        return setResult("500",msg,null);
    }


    public Map<String,Object> setResultOk(Object data){
        return setResult("200","ok",data);
    }

    public Map<String,Object> setResultSuccess(String msg){
        return setResult("200",msg,null);
    }

}
