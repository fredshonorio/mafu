package com.fredhonorio.mafu;

import java.util.function.Supplier;

import com.fredhonorio.mafu.MappingException.MissingOrWrongType;

public class Throw implements Supplier<MissingOrWrongType> {

	@Override
	public MissingOrWrongType get() {
		throw new MissingOrWrongType();
	}

	public static Throw build() {
		return new Throw();
	}

}
