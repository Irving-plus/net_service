package com.version.common.util;

import java.util.Random;

public class RandomUtil {

	private static Random random = new Random();
	/**
	 * 生成0-n直接的随机数 包括0 不包括n
	 * @param n
	 * @return
	 */
	public static int randInt(int n) {
		return random.nextInt(n);
	}
	/**
	 * 生成区间直接的随机数，半开区间(包括x1 不包括x2)
	 * @param x1 包括
	 * @param x2 包括含
	 * @return
	 */
	public static int randInt(int x1, int x2) {
		int temp = 0;
		if (x1 > x2) {
			temp = x1;
			x1 = x2;
			x2 = temp;
		}
		int rand = random.nextInt(x2 - x1);
		return rand + x1;
	}
	/**
	 * 百分比概率：如果概率匹配返回true
	 * @param n 对应百分之n
	 * @return
	 */
	public static boolean percent(int n) {
		return random.nextInt(100) < n;
	}
	/**
	 * 千分比概率：如果概率匹配返回true
	 * @param n 
	 * @return
	 */
	public static boolean permillage(int n) {
		return random.nextInt(1000) < n;
	}
}
