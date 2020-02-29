package com.version.network.webSocket;
import com.version.common.entity.client.AbstractClient;
import com.version.common.entity.client.WebSocketClient;
import com.version.session.ServerSessionManager;
import com.version.common.manager.TcpControllerManager;
import com.version.common.util.ConstantUtil;
import com.version.common.util.LoggerUtil;
import com.version.common.work.MessageWork;
import com.version.common.work.WorkManager;
import com.version.network.common.IoSender;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.Method;

/**
 * @Author 周希来
 * @Date 2019/9/2 14:50
 */
@ServerEndpoint(value = "/websocket/room",
      /*  ,
        encoders = { MessageEncoder.class },*/
        decoders = { WsDecoder.class }
)
@Component
public class WsHandle {

    public WsHandle(){

        LoggerUtil.info("初始化websocket");
    }
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        LoggerUtil.info("有连接加入，当前连接数为：{}");
        WebSocketClient WebSocketClient = new WebSocketClient(session,"websocket/room");
        // 将连接加入本地缓存管理器
        ServerSessionManager.getManager().putClient(WebSocketClient);
        LoggerUtil.info("初始化本地session"+session.toString());
        IoSender.sendWebsocketMsg(session,200,"链接成功");

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        AbstractClient superClient = ServerSessionManager.getManager().findClientBySession(session);
        try {
            ServerSessionManager.getManager().closeClient(superClient);
            LoggerUtil.info("连接断开"+session.getId());
        } catch (Exception e) {
            LoggerUtil.error("链接关闭异常");
            e.printStackTrace();
        }

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(WsMessage message, Session session) {
        LoggerUtil.info("来自客户端的消息：{}",message);
        long beginTime = System.currentTimeMillis();
        AbstractClient superClient = ServerSessionManager.getManager().findClientBySession(session);
        superClient.setAttribute(ConstantUtil.LAST_RECIVED_TIME, System.currentTimeMillis());
        System.out.println("websocket"+Thread.currentThread().getName());
        int code = message.getCode();
        Method method =  TcpControllerManager.getManager().getProcess(code);
        if (method != null) {
            // 逻辑业务异步处理,动态组装方法参数
            MessageWork messageWork = new MessageWork();
            //初始化用户加入逻辑服
            try {
                messageWork.init(method,message,superClient,beginTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

            WorkManager.getManager().submit(messageWork);
        } else {
            LoggerUtil.error("请求找不到处理器,消息号:" + message.getCode());
        }


        IoSender.sendWebsocketMsg(session,200,"123456乱码");

    }

    /**
     * 出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LoggerUtil.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

}
