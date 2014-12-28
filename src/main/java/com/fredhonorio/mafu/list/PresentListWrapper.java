package com.fredhonorio.mafu.list;

import java.util.Iterator;

import com.google.common.base.Supplier;

public abstract class PresentListWrapper<T> extends ListWrapper<T> {

	protected final Iterable<T> list;

	public PresentListWrapper(Iterable<T> list) {
		this.list = list;
	}

	public Iterable<T> get() {
		return list;
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public ListWrapper<T> or(ListWrapper<T> listW) {
		return this;
	}

	@Override
	public Iterable<T> or(Iterable<T> list) {
		return this.list;
	}

	@Override
	public Iterable<T> or(Supplier<Iterable<T>> listS) {
		return this.list;
	}

	@Override
	public boolean isPresent() {
		return true;
	}

}
