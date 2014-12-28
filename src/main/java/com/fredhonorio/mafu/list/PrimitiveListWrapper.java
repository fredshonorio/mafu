package com.fredhonorio.mafu.list;

import java.util.Iterator;

public class PrimitiveListWrapper<T> extends PresentListWrapper<T> {

	private final Class<T> cls;

	public PrimitiveListWrapper(Iterable<T> list, Class<T> cls) {
		super(list);
		this.cls = cls;
	}

	@Override
	public Iterator<T> iterator() {
		return new TypeCheckIterator<T>(list.iterator(), cls);
	}

}
