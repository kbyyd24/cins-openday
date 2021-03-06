package cn.edu.swpu.cins.openday.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogger {

	private Log logger = LogFactory.getLog(ServiceLogger.class);

	@Pointcut("execution(public * cn.edu.swpu.cins.openday.service.*.*(..))")
	public void service(){}

	@Before("service()")
	public void before(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String method = signature.getDeclaringTypeName() + '.' + signature.getName();
		logger.info("calling : " + method);
		Object[] args = joinPoint.getArgs();
		for (Object arg :
			args) {
			logger.info("arg : " + arg);
		}
	}

	@AfterReturning(pointcut = "service()", returning = "ret")
	public void afterReturn(Object ret) {
		logger.info("service return : " + ret);
	}

}
