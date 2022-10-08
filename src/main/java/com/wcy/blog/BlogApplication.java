package com.wcy.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author WuChongYuan
 * @version 1.0
 * @date 2022/9/25 21:03
 */

@MapperScan("com.wcy.blog.dao")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableScheduling
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }


}
