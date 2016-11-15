package cn.edu.swpu.cins.openday.config;

import cn.edu.swpu.cins.openday.config.interceptor.HttpRequestInterceptor;
import cn.edu.swpu.cins.openday.config.interceptor.MatchSecurityInterceptor;
import cn.edu.swpu.cins.openday.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	public WebAppConfig() {}

	private CacheService cacheService;

	@Autowired
	public WebAppConfig(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/*").allowedOrigins("*");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(new HttpRequestInterceptor())
			.addPathPatterns("/*");
		registry
			.addInterceptor(new MatchSecurityInterceptor(cacheService))
			.addPathPatterns("/match/*");
	}
}
