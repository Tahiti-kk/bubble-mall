package org.jerrylee.bubblemall.util.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author JerryLee
 * @date 2020/4/27
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket webApi(Environment environment) {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("外部接口")
                .apiInfo(webApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.jerrylee.bubblemall.controller"))
                .build()
                ;
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("API Documentation for Bubble Mall")
                .description("API-Doc")
                .contact(new Contact("Jerry Lee", "https://github.com/Tahiti-kk", "jyleewhu@gmail.com"))
                .version("1.0")
                .build();
    }

}
