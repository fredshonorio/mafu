package com.fredhonorio.mafu;

import java.util.Optional;

public abstract class Util {

	public static <T> T cast(Object v, Class<T> cls) {
		try {
			T t = cls.cast(v);
			return t;
		} catch (ClassCastException e) {
			throw new MappingException.WrongType(v, cls);
		}
	}

	public static <T> Optional<T> tryCast(Object v, Class<T> cls) {
		try {
			T t = cls.cast(v);
			return Optional.of(t);
		} catch (ClassCastException e) {
		}
		return Optional.empty();
	}

}
