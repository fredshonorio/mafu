package com.fredhonorio.mafu.list;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.fredhonorio.mafu.MappingException;

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
	public Iterable<T> orElse(Iterable<T> list) {
		return list;
	}

	@Override
	public Iterable<T> orElseGet(Supplier<Iterable<T>> listS) {
		return listS.get();
	}


	@Override
	public <X extends Throwable> Iterable<T> orElseThrow(Supplier<? extends X> exSup) throws X {
		throw exSup.get();
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
