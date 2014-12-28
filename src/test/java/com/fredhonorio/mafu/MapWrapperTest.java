package com.fredhonorio.mafu;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fredhonorio.mafu.list.ListWrapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class MapWrapperTest {

	@SuppressWarnings("rawtypes")
	public static Map JUDE = ImmutableMap.of("c1", "hey", "c2", "jude");
	public static List<String> ROSES = ImmutableList.of("roses", "are", "red", "violets", "are", "blue");
	public static List<ImmutableMap<String, String>> BAND = ImmutableList.of(ImmutableMap.of("name", "harry"),
			ImmutableMap.of("name", "the potters"));

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map MAP = ImmutableMap.copyOf(new HashMap() {
		private static final long serialVersionUID = 1L;
		{
			put("aString", "hey");
			put("aBoolean", false);
			put("aNumber", 10L);
			put("aMap", JUDE);
			put("aListOfStrings", ROSES);
			put("aListOfObjects", BAND);
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
		assertEquals(JUDE, map.object("MISSING_KEY").or(JUDE));
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

	@Test
	public void testStringList() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals(ROSES, map.stringList("aListOfStrings").get());
	}

	@Test(expected = MappingException.WrongType.class)
	public void testWrongObjectList() {
		MapWrapper map = MapWrapper.wrap(MAP);

		// this only fails with asList, once the first element is consumed, is
		// there another way?
		map.objectList("aListOfStrings").toList();
	}

	@Test
	public void testObjectList() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals(BAND, map.objectList("aListOfObjects").toList());
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testChecked() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals(BAND, map.stringList("nothing").or(Throw.forStringList()));
	}

	@Test
	public void testListOr() {
		Iterable<String> x = MapWrapper.wrap(MAP).stringList("axListOfStrings").or(ImmutableList.of("hey"));
		assertEquals(ImmutableList.of("hey"), x);
	}

	@Test(expected = MappingException.WrongType.class)
	public void testBadList() {

		MapWrapper m = MapWrapper.wrap(
				ImmutableMap.of(
						"badlist", ImmutableList.of("A", 2)
				)
		);

		for (Object x : m.stringList("badlist"))
			System.out.println(x);
	}

	@Test
	public void testFuture() {

		List<String> x = MapWrapper.wrap(MAP).stringList("aListOfStrings").toList();
		System.out.println(x);
	}
}
