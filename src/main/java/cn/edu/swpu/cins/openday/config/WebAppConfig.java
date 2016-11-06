package cn.edu.swpu.cins.openday.config;

import cn.edu.swpu.cins.openday.config.interceptor.UserSecurityInterceptor;
import cn.edu.swpu.cins.openday.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(new UserSecurityInterceptor(cacheService))
			.addPathPatterns("/activity/add");
	}
}