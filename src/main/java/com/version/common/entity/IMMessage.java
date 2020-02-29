package com.version.common.entity;

import java.io.Serializable;

import com.version.common.util.StringHelper;
import lombok.Data;

@Data
public class IMMessage implements Serializable{
	private static final long serialVersionUID = -1018719997646984034L;
	private final String msgId = StringHelper.randUUID();
	private String fromUniqueId;
	private long sendTime;
	private String toUniqueId;
	private int chanelType;
	private int msgType;
	private String msg;

}
