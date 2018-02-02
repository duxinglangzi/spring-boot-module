package com.andy.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: spring-boot 核心配置类 </p>
 * <p>Description: </p>
 * <p>@author wuqiong  2018/1/30 14:19 </p>
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "com.andy.application")
//@PropertySource("classpath:/author/auth.properties")//@PropertySource来指定自定义的资源目录
public class ApplicationConfig {

    private String baiduUrl;

}
