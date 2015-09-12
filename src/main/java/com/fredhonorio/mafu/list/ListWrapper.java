package com.fredhonorio.mafu.list;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.fredhonorio.mafu.Immutable;
import com.fredhonorio.mafu.MapWrapper;

/**
 * Defines a wrapper for a list.
 * 
 * @author fredh
 * 
 * @param <T>
 */
public abstract class ListWrapper<T> implements Iterable<T> {

	/*
	 * Static
	 */

	private static AbsentListWrapper<Object> absent = new AbsentListWrapper<Object>();

	public static ListWrapper<?> absent() {
		return absent;
	}

	@SuppressWarnings("unchecked")
	public static <T> ListWrapper<T> absent(Class<T> cls) {
		// No checks needed, the container is empty
		return (ListWrapper<T>) absent;
	}

	@SuppressWarnings("rawtypes")
	public static ListWrapper<MapWrapper> forObject(List<Map> list) {
		return new ObjectListWrapper(list);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ListWrapper<T> forPrimitive(List list, Class<T> cls) {
		// checks are done by the iterator
		return new PrimitiveListWrapper(list, cls);
	}

	/*
	 * Iterator
	 */

	public abstract Iterable<T> get();

	public abstract boolean isPresent();

	public abstract Iterator<T> iterator();

	public List<T> toList() {
		return Immutable.listFrom(iterator());
	}

	/*
	 * Optional<T> interface
	 */

	public abstract Iterable<T> orElse(Iterable<T> list);

	public abstract Iterable<T> orElseGet(Supplier<Iterable<T>> listS);

	public abstract <X extends Throwable> Iterable<T> orElseThrow(Supplier<? extends X> exSup) throws X;

	public abstract List<T> toList(Function<Object, Optional<T>> transform);
}