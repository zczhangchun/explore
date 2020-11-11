package com.monkey.spring.annotation.component.config;

import com.monkey.spring.annotation.component.filterType.MyFilterType;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author zhangchun
 */
@Configurable

//使用包含的时候，需要将useDefaultFilters关掉，includeFilters才能够起作用，否则就扫描全部
@ComponentScan(basePackages = {"com.monkey.spring.annotation.component"},useDefaultFilters = false,includeFilters = {
        //包含注解是Controller的
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class}),
        //自定义typeFilter
        @ComponentScan.Filter(type = FilterType.CUSTOM, value = {MyFilterType.class})
})

//排除
//@ComponentScan(basePackages = {"com.zhangchun.spring.annotation.component"}, excludeFilters = {
//                //排除注解是controller的bean
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class}),
//                //排除指定的class
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {MyService.class})})

//基本使用
//@ComponentScan(basePackages = {"com.zhangchun.spring.annotation.component"})
public class MainConfig {
}
