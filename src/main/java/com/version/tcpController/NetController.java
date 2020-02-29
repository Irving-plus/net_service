package com.version.tcpController;

import com.mchange.v2.log.LogUtils;
import com.version.common.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.version.common.annotation.IProcess;
import com.version.common.annotation.TCPController;
import com.version.common.manager.ThreadLocalManager;
import com.version.service.ExampleServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author 周希来
 * @Date 2019/9/4 18:57
 */
@TCPController(name = "net")
public class NetController extends BaseTcpController{
	@Autowired
	private ExampleServiceImpl serviceImpl;
	@Autowired
	RedisTemplate<Object,Object> redisTemplate;
	@IProcess(code =200)
	public void test(String json) {
		System.out.println("连接成功");
	}


	@IProcess(code =201)
	public void joinGame(String json) {
		redisTemplate.opsForValue().set("user",getController());
		String str = (String) redisTemplate.opsForValue().get("user");
		System.out.println(str);
		serviceImpl.doSomeThing("cbx");
	}
	
	@IProcess(code =202)
	public void join(String json) {
		long endTime = System.currentTimeMillis();
		LoggerUtil.info("执行时间"+(endTime -getBeginTime())+"毫秒");
	}
}
