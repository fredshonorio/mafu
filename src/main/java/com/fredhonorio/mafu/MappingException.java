package com.fredhonorio.mafu;

public abstract class MappingException extends RuntimeException {
	private static final long serialVersionUID = -4810485394378648386L;

	public MappingException(String message) {
		super(message);
	}

	public static class NoSuchKey extends MappingException {
		private static final long serialVersionUID = -2199030432346968112L;

		public NoSuchKey(Object key) {
			super("Key '" + String.valueOf(key) + "' does not exist.");
		}

		public NoSuchKey() {
			super("Value does not exist.");
		}
	}

	public static class MissingOrWrongType extends MappingException {
		private static final long serialVersionUID = 2362462151932731649L;

		public MissingOrWrongType() {
			super("Value is missing or has the wrong type");
		}

	}

	public static class CastException extends MappingException {
		private static final long serialVersionUID = -2199030432346968112L;

		@SuppressWarnings("rawtypes")
		public CastException(Object key, Class type, Class expected) {
			super("Key '" + String.valueOf(key) + "' is of type '" + type.getName() + "', should be '"
					+ expected.getName() + "'.");
		}
	}

	public static class Immutable extends MappingException {
		private static final long serialVersionUID = -2199030432346968112L;

		public Immutable() {
			super("Wrapper does not implement mutation operations.");
		}
	}

}
