package com.version.common.find;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import com.version.common.util.LoggerUtil;
/**
 *  动态校验类
 * @author 
 *
 */
public abstract class AbstractDynamicFind implements Find {
	private static Set<Class<?>> clazzs = new HashSet<Class<?>>();
	/**
	 * 读取该路径下 的类全路径
	 * 初始化claszzs
	 */
	@Override
	public void find() throws Exception {
		if (clazzs.isEmpty()) {
			clazzs = PackageScanner.scanPackages(Config.PACKAGE_SCAN_PATH);
		}
		for (Class<?> clz : clazzs) {
			findOne(clz);
		}
	}

	public void findOne(Class<?> clazz) throws Exception {
		if (verification(clazz)) {
			findClass(clazz);
		}
	}

	public abstract void findClass(Class<?> clz) throws Exception;

	public boolean isAbstract(Class<?> clazz) {
		return Modifier.isAbstract(clazz.getModifiers());
	}

	public boolean isInter(Class<?> clazz) {
		return clazz.isInterface();
	}

	public boolean interfaceOn(Class<?> clazz, Class<?> inter) {
		if (clazz.isInterface()) {
			return false;
		}
		if (clazz.isAnonymousClass()) {
			return false;
		}
		if (Modifier.isAbstract(clazz.getModifiers())) {
			return false;
		}
		Class<?>[] clzs = clazz.getInterfaces();
		for (Class<?> clz : clzs) {
			if (clz.equals(inter)) {
				return true;
			}
		}
		return false;
	}

	public boolean superClassOn(Class<?> clazz, Class<?> superClz) {
		if (clazz.isInterface()) {
			return false;
		}
		if (clazz.isAnonymousClass()) {
			return false;
		}
		if (Modifier.isAbstract(clazz.getModifiers())) {
			return false;
		}
		Class<?> clz = clazz.getSuperclass();
		return clz.equals(superClz);
	}

	public boolean annotationOn(Class<?> clazz, Class<? extends Annotation> annotationClz) {
		if (clazz.isInterface()) {
			return false;
		}
		if (clazz.isAnonymousClass()) {
			return false;
		}
		if (Modifier.isAbstract(clazz.getModifiers())) {
			return false;
		}
		return clazz.isAnnotationPresent(annotationClz);
	}

	public boolean haveSuperInterfaceOn(Class<?> clazz, Class<?> superInter) {
		if (clazz == null) {
			return false;
		}
		Class<?>[] clzs = clazz.getInterfaces();
		boolean find = false;
		for (Class<?> clz : clzs) {
			if (clz.equals(superInter)) {
				return true;
			} else {
				find = haveSuperInterfaceOn(clz, superInter);
			}
		}
		if (find) {
			return true;
		}
		Class<?> superClass = clazz.getSuperclass();
		return haveSuperInterfaceOn(superClass, superInter);
	}

	@Override
	public void start() throws Exception {
		try {
			beforeFind();
			find();
			afterFind();
		} catch (Exception e) {
			LoggerUtil.error(e);
			throw e;
		}
	}
}
