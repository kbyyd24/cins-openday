package cn.edu.swpu.cins.openday.config.interceptor;

import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserSecurityInterceptor implements HandlerInterceptor {

	private CacheService cacheService;

	@Autowired
	public UserSecurityInterceptor(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String mail = request.getHeader("openday-user");
		String token = request.getHeader("openday-token");
		AuthUser authUser = new AuthUser(mail, token);
		return cacheService.checkToken(authUser);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
