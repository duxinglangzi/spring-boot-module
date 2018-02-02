package com.andy.core;

import com.andy.core.annotation.FrameworkAnnotaion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>ClassName: 启动类 </p>
 * <p>Description: </p>
 * <p>@author wuqiong  2018/2/1 19:14 </p>
 */
@SpringBootApplication
@FrameworkAnnotaion(
        propertySourceValue = {"classpath:/bootstrap.properties"},
        componentScanPackages = {"com.andy"}
)
public class Application {


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);
    }


}
