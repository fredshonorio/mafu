package com.fredhonorio.mafu.list;

import java.util.List;

import com.fredhonorio.mafu.MapWrapper;
import com.fredhonorio.mafu.MappingException;
import com.google.common.collect.ImmutableList;

public abstract class ListWrapper<T> implements Iterable<T> {

	protected final Iterable<T> list;
	protected final boolean absent;

	protected ListWrapper(Iterable<T> list, boolean absent) {
		this.list = list;
		this.absent = absent;
	}

	@SuppressWarnings("rawtypes")
	public static <T> ListWrapper<T> forPrimitive(List list, Class<T> cls) {
		return new PrimitiveListWrapper<T>(list, false);
	}

	public static ListWrapper<MapWrapper> forObject(List<MapWrapper> list) {
		return new ObjectListWrapper(list, false);
	}

	public Iterable<T> get() {
		if (absent)
			throw new MappingException.MissingOrWrongType();

		return list;
	}

	public List<T> toList() {
		return ImmutableList.copyOf(iterator());
	}

}
