package com.yooni.intra.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggetApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.yooni.intra.common.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);

    }
    private ApiInfo swaggerInfo(){
        return new ApiInfoBuilder().title("Yooni Intra Documentation")
                .description("Yooni의 개발시 사용하는 서버 API 문서입니다.")
                .license("yoonilee").licenseUrl("http://www.yoonilee.com").version("1").build();
    }
}
