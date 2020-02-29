package com.version.common.util;

import java.security.MessageDigest;

public class MD5Util {

	public final static String md5x2(String s) {
		return md5(md5(s));
	}

	public final static String md5(String s, int b) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");

			digest.update(s.getBytes());
			switch (b) {
			case 16:
				return getEncode16(digest);
			case 64:
				LoggerUtil.warn("Temporarily not support 64 bit MD5 encryption!");
				return null;
			case 32:
			default:
				return md5(s);
			}
		} catch (Exception e) {
			LoggerUtil.error(e);
			return null;
		}
	}

	public final static String md5(String s) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			byte[] strTemp = s.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			LoggerUtil.error(e);
			return null;
		}
	}

	private static String getEncode16(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();
		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}

		return builder.substring(8, 24).toString();
	}
}
