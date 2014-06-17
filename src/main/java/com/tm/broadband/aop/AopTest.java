package com.tm.broadband.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class AopTest {

	public AopTest() {}
	
	//@Pointcut("execution(* com.tm.broadband.service.SystemService.queryUserLogin(..))")
	//@Pointcut("execution(* com.tm.broadband.controller.SystemRestController.userLogin(..))")
	public void query() {
		
	}
	
	//@Before("query()")
	public void print(){
		System.out.println("someone want to login.");
	}

}
