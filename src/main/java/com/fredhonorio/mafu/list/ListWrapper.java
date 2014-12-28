package com.fredhonorio.mafu.list;

import java.util.Iterator;
import java.util.List;

import com.fredhonorio.mafu.MapWrapper;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;

/**
 * TODO: Specify behavior when:
 * 
 * List has distinct types -> ["hey", 123] or ["hey", {"hey": 11}]. This is
 * hopefully not frequent enough but i'm not sure how to handle it.
 * 
 * Lists are evaluated lazily so you can't return an empty optional.
 * 
 * 
 * 
 * 
 * 
 * @author fredh
 * 
 * @param <T>
 */
public abstract class ListWrapper<T> implements Iterable<T> {

	public abstract Iterator<T> iterator();

	public abstract Iterable<T> get();

	public List<T> toList() {
		return ImmutableList.copyOf(iterator());
	}

	/*
	 * Optional<T> inteface
	 */

	public abstract ListWrapper<T> or(ListWrapper<T> listW);

	public abstract Iterable<T> or(Iterable<T> list);

	public abstract Iterable<T> or(Supplier<Iterable<T>> listS);

	public abstract boolean isPresent();

	private static AbsentListWrapper<Object> absent = new AbsentListWrapper<Object>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ListWrapper<T> forPrimitive(List list, Class<T> cls) {
		return new PrimitiveListWrapper(list, cls);
	}

	public static ListWrapper<MapWrapper> forObject(List<MapWrapper> list) {
		return new ObjectListWrapper(list);
	}

	public static ListWrapper<?> absent() {
		return absent;
	}

	@SuppressWarnings("unchecked")
	public static <T> ListWrapper<T> absent(Class<T> cls) {
		return (ListWrapper<T>) absent;
	}
}