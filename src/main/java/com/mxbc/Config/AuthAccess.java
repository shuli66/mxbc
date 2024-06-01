package com.mxbc.Config;

import java.lang.annotation.*;
//单个接口放行
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
}