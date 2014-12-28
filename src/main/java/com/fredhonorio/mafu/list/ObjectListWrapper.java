package com.fredhonorio.mafu.list;

import java.util.Iterator;
import java.util.Map;

import com.fredhonorio.mafu.MapWrapper;
import com.fredhonorio.mafu.MappingException;
import com.fredhonorio.mafu.Util;

public class ObjectListWrapper extends PresentListWrapper<MapWrapper> {

	public ObjectListWrapper(Iterable<MapWrapper> list) {
		super(list);
	}

	@Override
	public Iterator<MapWrapper> iterator() {
		return new ObjectListIterator(list.iterator());
	}

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
}
