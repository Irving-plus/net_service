package com.version.common.entity.message;

import java.io.Serializable;

import com.version.common.entity.client.AbstractClient;

public class Message implements Serializable{
	private static final long serialVersionUID = -9043895947534699359L;
	private int code;
	private int length;
	private byte[] data;
	private AbstractClient superClient;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public AbstractClient getSuperClient() {
		return superClient;
	}
	public void setSuperClient(AbstractClient superClient) {
		this.superClient = superClient;
	}
}
