package com.fredhonorio.mafu;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import com.fredhonorio.mafu.list.ListWrapper;

@SuppressWarnings("rawtypes")
public class MapWrapper implements Map {

	/*
	 * Static
	 */

	final static MapWrapper EMPTY_MAPWRAPPER = new MapWrapper();

	final static ListWrapper<String> EMPTY_STRLIST = ListWrapper.absent(String.class);
	final static ListWrapper<Long> EMPTY_NUMLIST = ListWrapper.absent(Long.class);
	final static ListWrapper<Boolean> EMPTY_BOOLLIST = ListWrapper.absent(Boolean.class);

	public static MapWrapper wrap(Map map) {
		return new MapWrapper(map);
	}

	/*
	 * Instance
	 */

	private final boolean absent;
	private final Map map;

	private MapWrapper(Map map) {
		this.map = map;
		this.absent = false;
	}

	private MapWrapper() {
		this.map = Collections.emptyMap();
		this.absent = true;
	}

	private <V> Optional<V> getAndCast(Object key, Class<V> cls) {

		if (map.containsKey(key)) {
			try {
				V v = cls.cast(map.get(key));
				return Optional.of(v);
			} catch (ClassCastException e) {
				return Optional.empty();
			}
		}

		return Optional.empty();
	}

	/*
	 * Accessors
	 */

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

	public ListWrapper<String> stringList(Object key) {
		Optional<List> inner = getAndCast(key, List.class);

		if (!inner.isPresent())
			return EMPTY_STRLIST;

		return ListWrapper.forPrimitive(inner.get(), String.class);
	}

	public ListWrapper<Long> numberList(Object key) {
		Optional<List> inner = getAndCast(key, List.class);

		if (!inner.isPresent())
			return EMPTY_NUMLIST;

		return ListWrapper.forPrimitive(inner.get(), Long.class);
	}

	public ListWrapper<Boolean> boolList(Object key) {
		Optional<List> inner = getAndCast(key, List.class);

		if (!inner.isPresent())
			return EMPTY_BOOLLIST;

		return ListWrapper.forPrimitive(inner.get(), Boolean.class);
	}

	@SuppressWarnings("unchecked")
	public ListWrapper<MapWrapper> objectList(Object key) {
		Optional<List> inner = getAndCast(key, List.class);

		if (!inner.isPresent())
			return ListWrapper.absent(MapWrapper.class);

		// this is checked by the iterator
		return ListWrapper.forObject(inner.get());
	}

	/*
	 * Optional<T> implementation
	 * TODO: adjust to java 8
	 */

	public Map get() {
		if (absent)
			throw new IllegalStateException("MapWrapper.get() cannot be called on an absent value");
		return map;
	}

	public Map or(Map map) {
		Assert.notNull(map);
		return !absent ? this.map : map;
	}

	public MapWrapper or(MapWrapper mapw) {
		Assert.notNull(mapw);
		return !absent ? this : mapw;
	}

	public Map or(Supplier<Map> map) {
		Assert.notNull(map);
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

}
