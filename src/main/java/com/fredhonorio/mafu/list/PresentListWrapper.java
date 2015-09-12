package com.fredhonorio.mafu.list;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class PresentListWrapper<T> extends ListWrapper<T> {

	@Override
	public boolean isPresent() {
		return true;
	}

	protected abstract Iterator<?> nativeIterator();

	@Override
	public Iterable<T> or(Iterable<T> list) {
		return this;
	}

	@Override
	public Iterable<T> or(Supplier<Iterable<T>> listS) {
		return this;
	}

	@Override
	public List<T> toList(Function<Object, Optional<T>> transform) {

		LinkedList<T> list = new LinkedList<>();
		Iterator<?> it = nativeIterator();

		while (it.hasNext()) {
			Optional<T> x = transform.apply(it.next());
			if (x.isPresent())
				list.add(x.get());
		}

		return Collections.unmodifiableList(list);
	}

}
