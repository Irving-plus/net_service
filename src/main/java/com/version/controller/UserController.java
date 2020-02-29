package com.version.controller;

import java.util.Map;
import java.util.Map.Entry;

import com.version.common.entity.client.AbstractClient;
import com.version.common.util.LoggerUtil;
import com.version.common.util.ShUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.version.common.entity.client.TcpSocketClient;
import com.version.session.ServerSessionManager;
import com.version.network.common.IoSender;
import com.version.network.tcp.TcpManager;

@RestController
public class UserController {
	@Autowired
	RedisTemplate<Object,Object> redisTemplate;
	@GetMapping("/severMess")
	public Object severMess(int code,String msg) {
		Map<String, AbstractClient> clients = ServerSessionManager.clients;
		for(Entry<String, AbstractClient> entroy: clients.entrySet() ) {
			AbstractClient client = entroy.getValue();
			if( client instanceof TcpSocketClient) {
				TcpSocketClient tcpSocketClient = (TcpSocketClient)client;IoSender.sendTcpMsg(tcpSocketClient.getSession(), code, msg);
				
			}
			//System.err.println(entroy.getKey());
		}
		
		return  JSONObject.toJSONString( clients.size()) ;
	}
	/*
	@GetMapping("/clientMess")
	public Object clientMess(int code,String mes) {
		ServerSessionManager sessionManager = ServerSessionManager.getManager();
		for(Entry<String,AbstractClient> entroy: sessionManager.clients.entrySet() ) {
			AbstractClient client = entroy.getValue();
			if( client instanceof TcpSocketClient) {
				TcpSocketClient tcpSocketClient = (TcpSocketClient)client;
				IoSender.sendTcpMsg(tcpSocketClient.getSession(), 200, "bingxi有点乖");
				
			}
			
			//System.err.println(entroy.getKey());
		}
		
		return  JSONObject.toJSONString( sessionManager.clients) ;
	}*/
	

	@GetMapping("/doTask")
	public Object doTask() {
	/*
		ExecutorService threadPool2 =  Executors.newFixedThreadPool(50);
        CompletionService<String> completionService = new ExecutorCompletionService<String>(threadPool2);
        
        for(int i=1;i<=10;i++){
            final int seq = i;
            completionService.submit(()->{
            	    int time = new Random().nextInt(5000);
                    Thread.sleep(time);
                    return  seq+"_"+time;
                
            });
        }
        long t1 = System.currentTimeMillis();
        for(int i=0;i<10;i++){
            try {
                System.out.println(
                        completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long t2 = System.currentTimeMillis();
		System.out.println("do time:" +(t2 - t1));
		return t2 -t1;*/
	return  "";
	}
	@GetMapping("/addClient")
	public void addClient() {
		try {
			TcpManager.getManager().createClientTcp("127.0.0.1","99");
			severMess(201, "cbx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@GetMapping("/test")
	public void test() {
		redisTemplate.opsForValue().set("user","zhou");
		String str = (String) redisTemplate.opsForValue().get("user");
		System.out.println(str);
	}

	/**
	 * 每日巡检
	 */
	@GetMapping("/check")
	public String check() {

		String str = ShUtil.exec("114.55.92.111","root","742042@Mn","./check.sh");
		String  flag = "----------:";
		String res = str.substring(str.indexOf(flag),str.length());
		System.out.println(res);
		LoggerUtil.info(res);
		return res;
	}

}
