package com.example.demo.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.sys.Interceptor.CommonInterceptor;

@SpringBootApplication
@MapperScan("com.example.demo.sys.Mapper")
@Configuration
public class DemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommonInterceptor Interceptor() {
		return new CommonInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(Interceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/css/**", "/js/**", "/login", "/error");
	}
}