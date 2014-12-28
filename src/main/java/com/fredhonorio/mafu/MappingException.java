package com.fredhonorio.mafu;

public abstract class MappingException extends RuntimeException {
	public static class Immutable extends MappingException {
		private static final long serialVersionUID = -1L;

		public Immutable() {
			super("Wrapper does not implement mutation operations.");
		}
	}

	public static class MissingOrWrongType extends MappingException {
		private static final long serialVersionUID = -1L;

		public MissingOrWrongType() {
			super("Value is missing or has the wrong type");
		}
	}

	public static class WrongType extends MappingException {

		private static final long serialVersionUID = -1L;

		@SuppressWarnings("rawtypes")
		public WrongType(Object a, Class expected) {
			super("Value '" + String.valueOf(a) + "' has the wrong type, expected: " + expected.getName());
		}

	}

	private static final long serialVersionUID = -1L;

	public MappingException(String message) {
		super(message);
	}

}
