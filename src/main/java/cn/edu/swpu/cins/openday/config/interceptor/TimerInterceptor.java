package cn.edu.swpu.cins.openday.config.interceptor;

import cn.edu.swpu.cins.openday.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TimerInterceptor implements HandlerInterceptor {

	private TimeService timeService;

	@Autowired
	public TimerInterceptor(TimeService timeService) {
		this.timeService = timeService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long current = timeService.getCurrentTimeMillis();
		long expire = timeService.getMatchExpireTime();
		if (current < expire)
			return true;
		response.sendError(500, "比赛已过期");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
