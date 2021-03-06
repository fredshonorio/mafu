package com.fredhonorio.mafu.list;

import java.util.Iterator;

import com.fredhonorio.mafu.MappingException;
import com.fredhonorio.mafu.Util;

class PrimitiveListWrapper<T> extends PresentListWrapper<T> {

	public static class TypeCheckIterator<T> implements Iterator<T> {

		protected final Class<T> cls;
		protected final Iterator<?> iter;

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
	private final Class<T> cls;

	private final Iterable<T> list;

	public PrimitiveListWrapper(Iterable<T> list, Class<T> cls) {
		this.cls = cls;
		this.list = list;
	}

	@Override
	public Iterable<T> get() {
		return this;
	}

	@Override
	public Iterator<T> iterator() {
		return new TypeCheckIterator<T>(list.iterator(), cls);
	}

	@Override
	protected Iterator<?> nativeIterator() {
		return list.iterator();
	}
}
