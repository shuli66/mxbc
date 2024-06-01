package com.mxbc.expection;


import com.mxbc.Config.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Map<String, Object> serviceException(ServiceException e) {
        return Result.setResultError2(e.getCode(),e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> globalException(Exception e) {
        e.printStackTrace();
        return Result.setResultEorMsg("系统错误");
    }


}