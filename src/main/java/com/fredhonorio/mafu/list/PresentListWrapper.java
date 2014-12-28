package com.fredhonorio.mafu.list;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;

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

		ImmutableList.Builder<T> list = ImmutableList.builder();
		Iterator<?> it = nativeIterator();

		while (it.hasNext()) {
			Optional<T> x = transform.apply(it.next());
			if (x.isPresent())
				list.add(x.get());
		}

		return list.build();
	}

}
