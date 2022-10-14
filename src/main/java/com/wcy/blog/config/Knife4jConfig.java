package com.wcy.blog.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/10:22
 */

@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class Knife4jConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .protocols(Collections.singleton("https"))
                .host("localhost:8080")
                .apiInfo(apiInfo())
                .select()
                // 设置扫描包的地址 : com.wcy.blog.controller
                .apis(RequestHandlerSelectors.basePackage("com.wcy.blog.controller"))
                // 设置路径筛选 只扫描com.wcy.blog.controller/test/下面的包
                // .paths(PathSelectors.ant("/test/**"))
                // com.wcy.blog.controller下的任何接口信息
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("博客api文档")
                .description("springboot+vue开发的博客项目")
                .contact(new Contact("Snail", "https://github.com/X1192176811", "1192176811@qq.com"))
                .termsOfServiceUrl("https://www.talkxj.com/api")
                .version("1.0")
                .build();
    }


}
