package com.fredhonorio.mafu.functions;

import com.fredhonorio.mafu.Util;
import com.google.common.base.Function;
import com.google.common.base.Optional;

public abstract class Lists {

	public static <T> Function<Object, Optional<T>> includeOnly(final Class<T> cls) {
		return new Function<Object, Optional<T>>() {

			@Override
			public Optional<T> apply(Object input) {
				return Util.tryCast(input, cls);
			}
		};
	}
}
