package com.fredhonorio.mafu;

import java.util.Map;

import com.google.common.base.Supplier;

public class ThrowChecked<T> implements Supplier<T> {

	@Override
	public T get() throws MappingException.MissingOrWrongType {
		throw new MappingException.MissingOrWrongType();
	}

	public static Supplier<String> forString() throws MappingException.MissingOrWrongType {
		return new ThrowChecked<String>();
	}

	public static Supplier<Long> forNumber() throws MappingException.MissingOrWrongType {
		return new ThrowChecked<Long>();
	}

	public static Supplier<Boolean> forBoolean() throws MappingException.MissingOrWrongType {
		return new ThrowChecked<Boolean>();
	}

	@SuppressWarnings("rawtypes")
	public static Supplier<Map> forMap() throws MappingException.MissingOrWrongType {
		return new ThrowChecked<Map>();
	}

}
