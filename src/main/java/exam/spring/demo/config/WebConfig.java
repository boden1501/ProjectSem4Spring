package exam.spring.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import exam.spring.demo.interceptor.AdminInterceptor;
import exam.spring.demo.interceptor.ClientInterceptor;
import exam.spring.demo.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/login");
		registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login","/admin/chklogin");

		registry.addInterceptor(new ClientInterceptor()).addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
