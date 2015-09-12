package com.fredhonorio.mafu.functions;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.fredhonorio.mafu.MapWrapper;
import com.fredhonorio.mafu.Util;

/**
 * Functions that filter and transform list objects.
 * 
 * @author fredh
 * 
 */
public abstract class Include {

	public static Function<Object, Optional<MapWrapper>> objects() {
		return new Function<Object, Optional<MapWrapper>>() {

			@Override
			public Optional<MapWrapper> apply(Object input) {

				@SuppressWarnings("rawtypes")
				Optional<Map> m = Util.tryCast(input, Map.class);

				if (m.isPresent())
					return Optional.of(MapWrapper.wrap(m.get()));

				return Optional.empty();
			}
		};
	}

	public static <T> Function<Object, Optional<T>> ofClass(final Class<T> cls) {
		return new Function<Object, Optional<T>>() {

			@Override
			public Optional<T> apply(Object input) {
				return Util.tryCast(input, cls);
			}
		};
	}
}
