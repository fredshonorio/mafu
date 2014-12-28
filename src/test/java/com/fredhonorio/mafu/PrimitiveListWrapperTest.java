package com.fredhonorio.mafu;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fredhonorio.mafu.functions.Include;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class PrimitiveListWrapperTest {

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
	public void testStringList() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals(ROSES, Lists.newLinkedList(map.stringList("aListOfStrings")));
	}

	@Test(expected = MappingException.WrongType.class)
	public void testWrongObjectList() {
		MapWrapper map = MapWrapper.wrap(MAP);
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

		MapWrapper m = MapWrapper.wrap(ImmutableMap.of("badlist", ImmutableList.of("A", 2)));
		m.stringList("badlist").toList();
	}

	@Test
	public void testTransformFilterOut() {

		MapWrapper m = MapWrapper.wrap(ImmutableMap.of("badlist", ImmutableList.of("A", 1, "B", 2, 0.0)));

		List<String> badlist = m.stringList("badlist").toList(Include.ofClass(String.class));

		assertEquals(badlist, ImmutableList.of("A", "B"));
	}
}
