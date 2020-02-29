package com.version.common.aop;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.NotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {
	private static final Logger log = LoggerFactory.getLogger(LogAop.class);
	
	/**
	 * @author 周希来
	 * @since 2019年3月27日
	 */
	public LogAop() {
		log.info("初始化controller方法追踪器成功！！！");

	}
	/**
	 * 拦截com.mywork+任何模块名称+controller+任意返回值，任意方法参数，当前包和子包的方法
	 */
	@Pointcut("execution(* com.version.tcpController..*.*(..))")
	public void pointUrl() {
		
	}
	@Before("pointUrl()")
	public void toPrintLog() {
	}
	@Around("pointUrl()")
	public Object  around(ProceedingJoinPoint pjd) throws Throwable {		
		//获取类名
		String className =pjd.getTarget().getClass().getName();
		String simpleName = pjd.getTarget().getClass().getSimpleName();	
		//获取方法名称
		String methodName = pjd.getSignature().getName();
	    // 定义返回参数
		Object result = null;
		long startTime = System.currentTimeMillis();
		Object[] args = pjd.getArgs();	
	    MethodSignature methodSignature = (MethodSignature) pjd.getSignature();    
	    //2.最关键的一步:通过这获取到方法的所有参数名称的字符串数组
	    String[] parameterNames = methodSignature.getParameterNames();
	    String strs = "";
		for(int i =0;i<parameterNames.length;i++) {			
			strs+=  parameterNames[i]+":"+ JSON.toJSONString(args[i])+"\t";
		}			
	    // 执行目标方法
	    result = pjd.proceed();
	    int line = ClassTool.getLine(className, methodName)-1;
	    //log.info("\n");
	    //打印调用方法位置
	    log.info("methodUrl:"  +"\n"+ className+"."+methodName+"\033[32;4m("+simpleName+".java:"+line+")"+"\033[0m");
	    log.info(" method:"+methodName); 
	    log.info(" params:" + strs);
	    //log.info(" data:"  +"\n"+ JSON.toJSONString(result));
	    // 获取执行完的时间
	    log.info(" total times:" + (System.currentTimeMillis() - startTime)+"\n");
	    return result;
	}
		
	 private static class ClassTool {
		
		public static ClassPool pool = ClassPool.getDefault();
		
		public static int getLine(String classPage,String method) {
			
			CtClass cc;
			try {
				cc = pool.getOrNull(classPage);
				if(cc!=null) {
					CtMethod methodX = cc.getDeclaredMethod(method);
					return methodX.getMethodInfo().getLineNumber(0);
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		     
		     return 1;
		}
	}

}
