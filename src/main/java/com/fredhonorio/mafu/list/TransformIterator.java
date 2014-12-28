package com.fredhonorio.mafu.list;

import java.util.Iterator;

import com.fredhonorio.mafu.MappingException;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public class TransformIterator<T, U> implements Iterator<U> {

	private final Iterator<T> inner;
	private final Function<T, Optional<U>> adapt;

	private Optional<U> next;

	public TransformIterator(Iterator<T> iter, Function<T, Optional<U>> adapt) {
		Preconditions.checkNotNull(iter);
		Preconditions.checkNotNull(adapt);

		this.inner = iter;
		this.adapt = adapt;
		next = getNext();
	}

	private Optional<U> getNext() {

		while (inner.hasNext()) {
			Optional<U> proc = adapt.apply(inner.next());

			if (proc.isPresent())
				return proc;
		}

		return Optional.absent();
	}

	@Override
	public boolean hasNext() {
		return next.isPresent();
	}

	@Override
	public U next() {

		U tmp = next.get();
		next = getNext();

		return tmp;
	}

	@Override
	public void remove() {
		throw new MappingException.Immutable();
	}

}
