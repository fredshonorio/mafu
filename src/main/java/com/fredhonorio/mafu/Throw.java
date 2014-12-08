package com.fredhonorio.mafu;

import java.util.Map;

import com.google.common.base.Supplier;

public class Throw<T> implements Supplier<T> {

	@Override
	public T get() {
		throw new MappingException.MissingOrWrongType();
	}

	public static Supplier<String> forString() {
		return new Throw<String>();
	}

	public static Supplier<Long> forNumber() {
		return new Throw<Long>();
	}

	public static Supplier<Boolean> forBoolean() {
		return new Throw<Boolean>();
	}

	@SuppressWarnings("rawtypes")
	public static Supplier<Map> forMap() {
		return new Throw<Map>();
	}

}
