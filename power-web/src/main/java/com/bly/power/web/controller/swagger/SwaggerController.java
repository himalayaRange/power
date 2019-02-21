package com.bly.power.web.controller.swagger;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.bly.power.web.constant.WebConstant;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SwaggerController {

	@Bean
	public Docket createRestApi(){
		 // 全局参数配置
		 // ParameterBuilder tokenPar = new ParameterBuilder();  
         // List<Parameter> paramters = new ArrayList<Parameter>();  
         // tokenPar.name("accessToken").description("访问令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();  
         // paramters.add(tokenPar.build());  
		 return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.bly.power.web.controller"))
				.paths(PathSelectors.any())
				.build();
		 //     .globalOperationParameters(paramters);
	}
	
	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("权限系统接口列表 v1.1.0")
	                .description("接口开发测试")
	                .termsOfServiceUrl(WebConstant.SwaggerServiceUrl)
	                .contact("佰丽源信息部")
	                .version("1.1.0")
	                .build();
	    }
}
