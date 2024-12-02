package com.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Before("execution(* com.example.controllers..*(..)) || execution(* com.example.services..*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		logger.info("Вызван метод: {}",joinPoint.getSignature());
	}
	
	@After("execution(* com.example.controllers..*(..)) || execution(* com.example.services..*(..))")
	public void afterAdvice(JoinPoint joinPoint) {
		logger.info("Метод выполнен: {}",joinPoint.getSignature());
	}
}
