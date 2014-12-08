package com.fredhonorio.mafu.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.fredhonorio.mafu.MapWrapper;
import com.fredhonorio.mafu.MappingException;
import com.fredhonorio.mafu.Util;
import com.google.common.collect.ImmutableList;

public class ObjectListWrapper extends ListWrapper<MapWrapper> {

	public static final ObjectListWrapper EMPTY = new ObjectListWrapper(
			ImmutableList.copyOf(new LinkedList<MapWrapper>()), true);

	public ObjectListWrapper(Iterable<MapWrapper> list, boolean absent) {
		super(list, absent);
	}

	@Override
	public Iterator<MapWrapper> iterator() {
		return new ObjListIterator(list.iterator());
	}

	private class ObjListIterator implements Iterator<MapWrapper> {

		public ObjListIterator(Iterator<?> iter) {
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
