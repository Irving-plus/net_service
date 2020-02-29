package com.version.service;

import com.version.common.util.LoggerUtil;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {
	public void doSomeThing(String str){
	
		LoggerUtil.info("执行service:"+ str);
		
	}
}
