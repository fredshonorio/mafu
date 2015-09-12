package com.fredhonorio.mafu;

public class Assert {

	public static void notNull(Object o) {
		if (o == null) {
			throw new AssertionError("Object is null");
		}
	}

}
