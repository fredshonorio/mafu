package com.fredhonorio.mafu.list;

import java.util.Iterator;

import com.fredhonorio.mafu.MappingException;
import com.fredhonorio.mafu.Util;

public class TypeCheckIterator<T> implements Iterator<T> {

	protected final Iterator<?> iter;
	protected final Class<T> cls;

	public TypeCheckIterator(Iterator<?> iter, Class<T> cls) {
		this.iter = iter;
		this.cls = cls;
	}

	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}

	@Override
	public T next() {
		return Util.cast(iter.next(), cls);
	}

	@Override
	public void remove() {
		throw new MappingException.Immutable();
	}
}
