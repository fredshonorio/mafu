package com.fredhonorio.mafu;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MapWrapperTest {

	public static Map<String, String> JUDE = Immutable.map("c1", "hey", "c2", "jude");
	public static List<String> ROSES = Arrays.asList("roses", "are", "red", "violets", "are", "blue");

	public static Map<String, ?> MAP = Immutable.map(
		"aString", "hey",
		"aBoolean", false,
		"aNumber", 10L,
		"aMap", JUDE,
		"anotherMap", JUDE,
		"aListOfStrings", ROSES
	);

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
		map.string("MISSING").orElseGet(Throw.forString());
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testWrongType() {
		MapWrapper map = MapWrapper.wrap(MAP);
		map.string("b").orElseGet(Throw.forString());
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
		assertEquals(JUDE, map.object("MISSING_KEY").orElse(JUDE));
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testOrThrow() {
		MapWrapper map = MapWrapper.wrap(MAP);
		map.object("MISSING_MAP").orElseGet(Throw.forObject());
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testOrElseThrow() throws Throwable {
		MapWrapper map = MapWrapper.wrap(MAP);
		map.object("MISSING_MAP").orElseThrow(() -> new MappingException.MissingOrWrongType());
	}

	@Test
	public void testOrMapWrapper() {
		MapWrapper map = MapWrapper.wrap(MAP);
		MapWrapper alt = MapWrapper.wrap(JUDE);
		assertEquals(JUDE, map.object("MISSING_MAP").orElse(alt).get());
	}

}
