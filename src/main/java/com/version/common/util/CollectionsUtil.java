package com.version.common.util;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionsUtil {

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> packageToCollection(T... ts) {
		Collection<T> r = new ArrayList<T>();
		for (T t : ts) {
			r.add(t);
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> packageToArrayList(T... ts) {
		ArrayList<T> r = new ArrayList<T>();
		for (T t : ts) {
			r.add(t);
		}
		return r;
	}
}
