package com.fredhonorio.mafu;

import java.util.Map;

import com.google.common.base.Supplier;

public class Throw<T> implements Supplier<T> {

	public static Supplier<Boolean> forBool() {
		return new Throw<Boolean>();
	}

	public static Supplier<Iterable<Boolean>> forBoolList() {
		return new Throw<Iterable<Boolean>>();
	}

	public static Supplier<Long> forNumber() {
		return new Throw<Long>();
	}

	public static Supplier<Iterable<Long>> forNumberList() {
		return new Throw<Iterable<Long>>();
	}

	@SuppressWarnings("rawtypes")
	public static Supplier<Map> forObject() {
		return new Throw<Map>();
	}

	@SuppressWarnings("rawtypes")
	public static Supplier<Iterable<Map>> forObjectList() {
		return new Throw<Iterable<Map>>();
	}

	public static Supplier<String> forString() {
		return new Throw<String>();
	}

	public static Supplier<Iterable<String>> forStringList() {
		return new Throw<Iterable<String>>();
	}

	@Override
	public T get() {
		throw new MappingException.MissingOrWrongType();
	}

}
