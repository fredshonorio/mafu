package com.fredhonorio.mafu.list;

import java.util.Iterator;

import com.fredhonorio.mafu.MappingException;
import com.fredhonorio.mafu.Util;
import com.fredhonorio.mafu.functions.Include;

class PrimitiveListWrapper<T> extends PresentListWrapper<T> {

	private final Class<T> cls;
	private final Iterable<T> list;

	public PrimitiveListWrapper(Iterable<T> list, Class<T> cls) {
		this.cls = cls;
		this.list = list;
	}

	@Override
	public Iterator<T> iterator() {
		return new TypeCheckIterator<T>(list.iterator(), cls);
	}

	public static class TypeCheckIterator<T> implements Iterator<T> {

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

	@Override
	protected Iterator<?> nativeIterator() {
		return list.iterator();
	}

	@Override
	public Iterable<T> get() {
		return this;
	}

	@Override
	public Iterable<T> safe() {
		return adapt(Include.ofClass(cls));
	}

}
