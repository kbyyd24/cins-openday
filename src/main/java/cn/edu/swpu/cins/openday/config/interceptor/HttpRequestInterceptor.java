package cn.edu.swpu.cins.openday.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

public class HttpRequestInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("Request URL : " + request.getRequestURL());
		System.out.println("Method : " + request.getMethod());
		Enumeration<String> headerNames = request.getHeaderNames();
		System.out.println("============================ header ============================");
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			String header = request.getHeader(name);
			System.out.println(name + " : " + header);
		}
		System.out.println("============================ end header ============================");
		Map<String, String[]> parameterMap = request.getParameterMap();
		System.out.println("============================ param ============================");
		parameterMap.forEach((key, value) -> System.out.println(key + " : " + value.toString()));
		System.out.println("============================ param end ============================");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
