package com.despegar.moku.web;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.despegar.moku.util.JsonUtils;

@Aspect
public class RestControllerLoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(RestControllerLoggingAspect.class);

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void isRestController() {
	}

	@Pointcut("execution(* *(..))")
	public void methodPointcut() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMapping() {
	}

	@Before("isRestController() && methodPointcut() && requestMapping()")
	public void aroundControllerMethod(JoinPoint joinPoint) throws Throwable {
		try {
			StringBuilder sb = new StringBuilder();
			Signature sig = joinPoint.getSignature();
			sb.append("Invoking method " + sig.getDeclaringTypeName() + "." + sig.getName() + "(");

			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				Object object = joinPoint.getArgs()[i];
				sb.append(" arg" + i + " = ");
				sb.append(JsonUtils.writer().writeValueAsString(object));
			}
			sb.append(")");
			logger.info(sb.toString());
		} catch (Throwable t) {
			logger.error("Fail trying to logging method invocation", t);
		}
	}

	@AfterReturning("isRestController() && methodPointcut() && requestMapping()")
	public void afterControllerMethod(JoinPoint joinPoint) {
		try {
			Signature sig = joinPoint.getSignature();
			logger.info("Invoked method " + sig.getDeclaringTypeName() + "." + sig.getName());
		} catch (Throwable t) {
			logger.error("Fail trying to logging method invoked", t);
		}
	}
}
