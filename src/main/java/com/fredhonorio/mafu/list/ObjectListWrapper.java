package com.fredhonorio.mafu.list;

import java.util.Iterator;
import java.util.Map;

import com.fredhonorio.mafu.MapWrapper;
import com.fredhonorio.mafu.MappingException;
import com.fredhonorio.mafu.Util;

@SuppressWarnings("rawtypes")
class ObjectListWrapper extends PresentListWrapper<MapWrapper> {

	private final Iterable<Map> list;

	public ObjectListWrapper(Iterable<Map> list) {
		this.list = list;
	}

	@Override
	public Iterator<MapWrapper> iterator() {
		return new ObjectListIterator(list.iterator());
	}

	/**
	 * An iterator that wraps a map iterator, checks if the elements are indeed
	 * maps and wraps each one around a MapWrapper.
	 * 
	 * @author fredh
	 * 
	 */
	private class ObjectListIterator implements Iterator<MapWrapper> {

		public ObjectListIterator(Iterator<?> iter) {
			this.iter = iter;
		}

		public final Iterator<?> iter;

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public MapWrapper next() {
			return MapWrapper.wrap(Util.cast(iter.next(), Map.class));
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
	public Iterable<MapWrapper> get() {
		return this;
	}
}
