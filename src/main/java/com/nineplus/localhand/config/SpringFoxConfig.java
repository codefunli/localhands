package com.nineplus.localhand.config;

import com.nineplus.localhand.utils.CommonConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalRequestParameters(
                        Arrays.asList(new RequestParameterBuilder()
                                        .name(CommonConstants.Authentication.ACCESS_TOKEN)
                                        .description("Access Token")
                                        .in(ParameterType.HEADER)
                                        .required(false)
                                        .build(),
                                new RequestParameterBuilder()
                                        .name(CommonConstants.Authentication.REFRESH_TOKEN)
                                        .description("Refresh Token")
                                        .in(ParameterType.HEADER)
                                        .required(false)
                                        .build(),
                                new RequestParameterBuilder()
                                        .name(CommonConstants.Authentication.PREFIX_TOKEN)
                                        .description("Prefix Token")
                                        .in(ParameterType.HEADER)
                                        .required(false)
                                        .build()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
