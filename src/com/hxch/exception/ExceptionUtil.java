package com.hxch.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:ExceptionUtil
 * @Description:TODO
 * @Author:huangxc
 * @Date: 2020/2/24 0024 18:29
 **/

@ControllerAdvice(basePackages="com.hxch.handler")
public class ExceptionUtil {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody//返回json串
    public Map<String, Object> errorResoult() {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put("errorCode", "500");
        errorMap.put("errorMsg", "系统错误");
        return errorMap;
    }

}
