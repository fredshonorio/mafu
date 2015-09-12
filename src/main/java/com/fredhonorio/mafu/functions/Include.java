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
		return (o) -> Util.tryCast(o, Map.class).map(m -> MapWrapper.wrap(m));
	}

	public static <T> Function<Object, Optional<T>> ofClass(final Class<T> cls) {
		return (o) -> Util.tryCast(o, cls);
	}
}
