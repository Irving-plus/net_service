package com.version.network.netty;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * <pre>
 * 数据包格式 解码器
 * | 命令号  | 总长度  |  数据     |
 * </pre>
 */
public class NettyDecoder extends LengthFieldBasedFrameDecoder {
	private final static byte[] zeroData = new byte[0];

	public NettyDecoder() throws IOException {
		super(2048, 4, 4, -8, 0);
		
	}

	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf buffer = null;
		try {
			// 调用父类进行拆包处理
			buffer = (ByteBuf) super.decode(ctx, in);
			if (buffer == null) {
				return null;
			}
			return encodeToMessage(buffer);
		} catch (Exception e) {
			ctx.channel().close();
			throw e;
		} finally {
			if (buffer != null) {
				buffer.release();
			}
		}
	}

	private TcpMessage encodeToMessage(ByteBuf buffer) {
		int code = buffer.readInt();
		int length = buffer.readInt();
		// 基于长度拆包，剩余可读取数据一定是数据部分
		byte[] data = null;
		if (buffer.readableBytes() > 0) {
			data = new byte[buffer.readableBytes()];
			buffer.readBytes(data);
		} else {
			data = zeroData;
		}
		TcpMessage message = new TcpMessage();
		message.setCode(code);
		message.setLength(length);
		message.setData(data);
		return message;
	}
}
