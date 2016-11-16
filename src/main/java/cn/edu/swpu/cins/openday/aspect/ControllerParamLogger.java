package cn.edu.swpu.cins.openday.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerParamLogger {

	private final Log logger = LogFactory.getLog(ControllerParamLogger.class);

	@Pointcut(value = "execution(public * cn.edu.swpu.cins.openday.controller.*.*(..))")
	public void controllerLog(){}

	@Before("controllerLog()")
	public void before(JoinPoint point) {
		logger.info("controller aspect beginning");
		Object[] args = point.getArgs();
		for (Object arg : args) {
			logger.info("arg : " + arg);
		}
		String method = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
		logger.info("aspect finishing");
		logger.info("calling " + method);
	}

}
