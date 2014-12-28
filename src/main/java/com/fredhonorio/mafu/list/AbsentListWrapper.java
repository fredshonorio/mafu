package com.fredhonorio.mafu.list;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fredhonorio.mafu.MappingException;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;

class AbsentListWrapper<T> extends ListWrapper<T> {

	protected AbsentListWrapper() {
	}

	@Override
	public Iterable<T> get() {
		throw new MappingException.MissingOrWrongType();
	}

	@Override
	public boolean isPresent() {
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return Collections.emptyIterator();
	}

	@Override
	public Iterable<T> or(Iterable<T> list) {
		return list;
	}

	@Override
	public Iterable<T> or(Supplier<Iterable<T>> listS) {
		return listS.get();
	}

	@Override
	public List<T> toList() {
		return Collections.emptyList();
	}

	@Override
	public List<T> toList(Function<Object, Optional<T>> transform) {
		return Collections.emptyList();
	}
}
