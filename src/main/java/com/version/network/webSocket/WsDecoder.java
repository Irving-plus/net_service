package com.version.network.webSocket;

import com.alibaba.fastjson.JSON;
import com.version.common.util.LoggerUtil;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;

/**
 * @Author 周希来
 * @Date 2019/9/4 14:54
 */
public class WsDecoder implements Decoder.Binary<WsMessage> {

    @Override
    public void init(EndpointConfig endpointConfig) {
        LoggerUtil.info("初始化websocket解码器"+endpointConfig.getUserProperties().toString());
    }

    @Override
    public void destroy() {
        LoggerUtil.info("销毁");
    }

    @Override
    public WsMessage decode(ByteBuffer buffer)  {
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        WsMessage msg =  JSON.parseObject(bytes,WsMessage.class);
        try {
            msg.setData(URLDecoder.decode(msg.getData().toString() , "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg ;
    }

    @Override
    public boolean willDecode(ByteBuffer bytes) {
        if(bytes.hasRemaining()){
            return  true;
        }
        return false;
    }
/*
    @Override
    public WsMessage decode(String s) throws DecodeException {
       // JSON.parseObject(s,WsMessage.class);
        System.out.println(s);
        return    JSON.parseObject(s,WsMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }*/
}
