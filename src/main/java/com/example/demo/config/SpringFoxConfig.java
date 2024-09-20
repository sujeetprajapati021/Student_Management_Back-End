package com.example.demo.config;


import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringFoxConfig {
	
	@Bean
	public Docket postsApi() {

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("public-api")
				.apiInfo(apiInfo())
				.select()
				.apis((Predicate<RequestHandler>) RequestHandlerSelectors.basePackage("com.studentapp"))
				.paths((Predicate<String>) PathSelectors.any())
				.build()
				.securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(apiKey()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Student APIs")
				.description("Student API reference for developers")
				.termsOfServiceUrl("http://student.com")
				.license("Student License")
				.licenseUrl("studentabc@gmail.com")
				.version("1.0")
				.build();
	}

//	@SuppressWarnings("deprecation")
//	@Bean
//	springfox.documentation.swagger.web.SecurityConfiguration security() {
//		return new springfox.documentation.swagger.web.SecurityConfiguration(null, null, null, null, "",
//				ApiKeyVehicle.HEADER, "AUTHORIZATION", null);
//	}

	private List<ApiKey> apiKey() {
		return Arrays.asList(
				new ApiKey("AUTHORIZATION", "Authorization", "header"),
				new ApiKey("Api Key", "x-api-key", "header")
		);
	}

	private SecurityContext securityContext() {
		return SecurityContext
				.builder()
				.securityReferences(defaultAuth())
				.forPaths((Predicate<String>) PathSelectors.regex("/api.*"))
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(
				new SecurityReference("AUTHORIZATION", authorizationScopes),
				new SecurityReference("Api Key", authorizationScopes)
		);
	}

}

