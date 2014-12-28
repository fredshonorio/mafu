package com.fredhonorio.mafu;

import java.util.Map;

import com.google.common.base.Supplier;

/**
 * Although the constructor methods (forString, forBoolean, etc) declare
 * ```throws CheckedMappingException.MissingOrWrongType``` the exception is
 * actually thrown when the ```or()``` method calls the suppliers ```get()```
 * method. This just makes sure you have to catch the exception.
 * 
 * @author fredh
 * 
 * @param <T>
 */
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
