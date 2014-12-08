package com.fredhonorio.mafu;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class MapWrapperTest {

	@SuppressWarnings("rawtypes")
	public static Map JUDE = ImmutableMap.of("c1", "hey", "c2", "jude");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map MAP = ImmutableMap.copyOf(new HashMap() {
		private static final long serialVersionUID = 1L;
		{
			put("aString", "hey");
			put("aBoolean", false);
			put("aNumber", 10L);
			put("aMap", JUDE);
		}
	});

	@Test
	public void testBasic() {

		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals("hey", map.string("aString").get());
		assertEquals(false, map.bool("aBoolean").get());
		assertEquals(new Long(10L), map.number("aNumber").get());
		assertEquals(JUDE, map.object("aMap").get());
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testMissing() {
		MapWrapper map = MapWrapper.wrap(MAP);
		map.string("MISSING").or(Throw.forString());
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testWrongType() {
		MapWrapper map = MapWrapper.wrap(MAP);
		map.string("b").or(Throw.forString());
	}

	@Test
	public void testNestedBasic() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals("hey", map.object("aMap").string("c1").get());
		assertEquals("jude", map.object("aMap").string("c2").get());
	}

	@Test
	public void testOrMap() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals(JUDE, map.object("MISSING_MAP").or(JUDE));
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testOrThrow() {
		MapWrapper map = MapWrapper.wrap(MAP);
		map.object("MISSING_MAP").or(Throw.forMap());
	}

	@Test
	public void testOrMapWrapper() {
		MapWrapper map = MapWrapper.wrap(MAP);
		MapWrapper alt = MapWrapper.wrap(JUDE);

		assertEquals(JUDE, map.object("MISSING_MAP").or(alt).get());
	}
}
