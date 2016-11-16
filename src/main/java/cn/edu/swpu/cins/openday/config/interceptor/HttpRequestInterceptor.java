package cn.edu.swpu.cins.openday.config.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class HttpRequestInterceptor implements HandlerInterceptor {


	private Log logger = LogFactory.getLog(HttpServletRequest.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("======= request begin =======");
		logger.info("Request URL : " + request.getRequestURL());
		logger.info("Method : " + request.getMethod());
		Enumeration<String> headerNames = request.getHeaderNames();
		logger.info("=== header ===");
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			String header = request.getHeader(name);
			logger.info(name + " : " + header);
		}
		logger.info("=== end header ===");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.info("======= request finish =======");
	}
}
