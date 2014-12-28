package com.fredhonorio.mafu;

public abstract class Util {

	public static <T> T cast(Object v, Class<T> cls) {
		try {
			T t = cls.cast(v);
			return t;
		} catch (ClassCastException e) {
			throw new MappingException.WrongType(v, cls);
		}
	}

}
