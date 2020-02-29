package com.version.network.webSocket;
import com.version.common.entity.message.IMessage;
import lombok.Data;
/**
 * @Author 周希来
 * @Date 2019/9/4 18:57
 */
@Data
public class WsMessage implements IMessage {

	private int code;
	private Object data;


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public Object getData(Class clazz) {
        return data;
    }
}
