package com.fredhonorio.mafu.list;

import java.util.Iterator;

import com.google.common.collect.ImmutableList;

public class PrimitiveListWrapper<T> extends ListWrapper<T> {

	protected PrimitiveListWrapper(Iterable<T> list, boolean absent) {
		super(list, absent);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final PrimitiveListWrapper EMPTY = new PrimitiveListWrapper(ImmutableList.of(), true);

	// TODO: override iterator to check cast and throw appropriate exception

}
