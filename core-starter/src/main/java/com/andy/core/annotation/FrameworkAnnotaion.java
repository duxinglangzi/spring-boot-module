package com.andy.core.annotation;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.AliasFor;

/**
 * <p>ClassName: 自定义核心注解 </p>
 * <p>Description: 自定义一些注解，可以直接放在 Application.java上使用  </p>
 * <p>@author wuqiong  2018/2/2 15:00 </p>
 */
@PropertySource(value = {})
public @interface FrameworkAnnotaion {

    /**
     * 按资源名称加载环境配置{@link PropertySource#name()}
     *
     * @return String
     *
     */
    @AliasFor(annotation = PropertySource.class, attribute = "name")
    String propertySourceName() default "";

    /**
     * 按资源位置加载环境配置{@link PropertySource#value()}
     *
     * @return String[]
     *
     */
    @AliasFor(annotation = PropertySource.class, attribute = "value")
    String[] propertySourceLocations();

}
