package com.fredhonorio.mafu;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class Immutable {

	/**
	 * Consumes an iterator and returns a fully evaluated list with its
	 * contents.
	 * 
	 * @param it
	 * @return
	 */
	public static <T> List<T> listFrom(Iterator<T> it) {

		LinkedList<T> l = new LinkedList<>();

		while (it.hasNext())
			l.add(it.next());

		return Collections.unmodifiableList(l);
	}

	/**
	 * Consumes an iterable's iterator and returns a fully evaluated list with
	 * its contents.
	 * 
	 * @param it
	 * @return
	 */
	public static <T> List<T> listFrom(Iterable<T> it) {
		return listFrom(it.iterator());
	}

	public static <K, V> Map<K, V> map(K k1, V v1) {
		TreeMap<K, V> map = new TreeMap<>();

		map.put(k1, v1);

		return Collections.unmodifiableMap(map);
	}

	public static <K, V> Map<K, V> map(K k1, V v1, K k2, V v2) {
		TreeMap<K, V> map = new TreeMap<>();

		map.put(k1, v1);
		map.put(k2, v2);

		return Collections.unmodifiableMap(map);
	}

	public static <K, V> Map<K, V> map(K k1, V v1, K k2, V v2, K k3, V v3, K k4,
			V v4, K k5, V v5, K k6, V v6) {
		TreeMap<K, V> map = new TreeMap<>();

		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		map.put(k4, v4);
		map.put(k5, v5);
		map.put(k6, v6);

		return Collections.unmodifiableMap(map);
	}

	public static <T> List<T> list(T t1) {
		LinkedList<T> l = new LinkedList<>();

		l.add(t1);

		return Collections.unmodifiableList(l);
	}

	public static <T> List<T> list(T t1, T t2) {
		LinkedList<T> l = new LinkedList<>();

		l.add(t1);
		l.add(t2);

		return Collections.unmodifiableList(l);
	}

	public static <T> List<T> list(T t1, T t2, T t3, T t4) {
		LinkedList<T> l = new LinkedList<>();

		l.add(t1);
		l.add(t2);
		l.add(t3);
		l.add(t4);

		return Collections.unmodifiableList(l);
	}

	public static <T> List<T> list(T t1, T t2, T t3, T t4, T t5, T t6) {
		LinkedList<T> l = new LinkedList<>();

		l.add(t1);
		l.add(t2);
		l.add(t3);
		l.add(t4);
		l.add(t5);
		l.add(t6);

		return Collections.unmodifiableList(l);
	}
}