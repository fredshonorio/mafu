package com.fredhonorio.mafu;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;

@SuppressWarnings("rawtypes")
public class MapWrapper implements Map {

	/*
	 * Static
	 */

	private final static MapWrapper EMPTY_MAPWRAPPER = new MapWrapper();

	// private final static ListWrapper emptyStringList = new
	// ListWrapper<String>(new LinkedList<String>());
	// private final static ListWrapper emptyObjectList = new
	// ListWrapper.ObjectListWrapper(new LinkedList<Map>());

	public static MapWrapper wrap(Map map) {
		return new MapWrapper(map);
	}

	private final boolean absent;
	private final Map map;

	private MapWrapper(Map map) {
		this.map = map;
		this.absent = false;
	}

	private MapWrapper() {
		this.map = ImmutableMap.of();
		this.absent = true;
	}

	/*
	 * Accessors
	 */

	private <V> Optional<V> getAndCast(Object key, Class<V> cls) {

		if (map.containsKey(key)) {
			try {
				V v = cls.cast(map.get(key));
				return Optional.of(v);
			} catch (ClassCastException e) {
				return Optional.absent();
			}
		}

		return Optional.absent();
	}

	public Optional<String> string(Object key) {
		return getAndCast(key, String.class);
	}

	public Optional<Long> number(Object key) {
		return getAndCast(key, Long.class);
	}

	public Optional<Boolean> bool(Object key) {
		return getAndCast(key, Boolean.class);
	}

	public MapWrapper object(Object key) {
		Optional<Map> inner = getAndCast(key, Map.class);

		if (!inner.isPresent())
			return EMPTY_MAPWRAPPER;

		return new MapWrapper((Map) map.get(key));
	}

	// TODO: lists(strings, objects, longs, booleans)

	/*
	 * Optional interface implementation
	 */

	public Map get() {
		if (absent)
			throw new IllegalStateException("MapWrapper.get() cannot be called on an absent value");
		return map;
	}

	public Map or(Map map) {
		Preconditions.checkNotNull(map);
		return !absent ? this.map : map;
	}

	public MapWrapper or(MapWrapper mapw) {
		Preconditions.checkNotNull(mapw);
		return !absent ? this : mapw;
	}

	public Map or(Supplier<Map> map) {
		Preconditions.checkNotNull(map);
		return !absent ? this.map : map.get();
	}

	/*
	 * Map interface implementation
	 */

	@Override
	public void clear() {
		throw new MappingException.Immutable();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set entrySet() {
		return map.entrySet();
	}

	@Override
	public Object get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set keySet() {
		return map.keySet();
	}

	@Override
	public Object put(Object key, Object value) {
		throw new MappingException.Immutable();

	}

	@Override
	public void putAll(Map m) {
		throw new MappingException.Immutable();

	}

	@Override
	public Object remove(Object key) {
		throw new MappingException.Immutable();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection values() {
		return map.values();
	}

	//
	// @SuppressWarnings("unchecked")
	// public Iterable<MapWrapper> valuesAsObjects() {
	// return new ListWrapper.ObjectListWrapper(map.values());
	// }
	//
	// @SuppressWarnings("unchecked")
	// public ListWrapper<String> stringList(Object key) {
	// return new ListWrapper<>((Iterable<String>) map.get(key));
	// }
	//
	// @SuppressWarnings("unchecked")
	// public ListWrapper<String> stringListOrEmpty(Object key) {
	// if (!map.containsKey(key)) {
	// return emptyStringList;
	// }
	//
	// return new ListWrapper<>((Iterable<String>) map.get(key));
	// }
	//
	// @SuppressWarnings("unchecked")
	// public ListWrapper<MapWrapper> objectListOrEmpty(Object key) {
	// if (!map.containsKey(key))
	// return emptyObjectList;
	//
	// return new ListWrapper.ObjectListWrapper((Iterable) map.get(key));
	// }
	//
	// @SuppressWarnings("unchecked")
	// public ListWrapper<MapWrapper> objectList(Object key) {
	// if (!map.containsKey(key)) {
	// throw new KeyDoesNotExist(key.toString(), map);
	// }
	//
	// return new ListWrapper.ObjectListWrapper((Iterable) map.get(key));
	// }
	//
	// @Override
	// public String toString() {
	// return map.toString();
	// }
	//

}
