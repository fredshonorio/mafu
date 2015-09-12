package com.fredhonorio.mafu;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fredhonorio.mafu.functions.Include;
import static com.fredhonorio.mafu.Immutable.*;

public class PrimitiveListWrapperTest {

	public static Map<String, String> JUDE = map("c1", "hey", "c2", "jude");
	public static List<String> ROSES = list("roses", "are", "red", "violets",
			"are", "blue");
	public static List<Map<String, String>> BAND = list(map("name", "harry"),
			map("name", "the potters"));

	@SuppressWarnings({ "rawtypes" })
	public static Map MAP = map("aString", "hey", "aBoolean", false, "aNumber",
			10L, "aMap", JUDE, "aListOfStrings", ROSES, "aListOfObjects", BAND);

	@Test
	public void testStringList() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals(ROSES, listFrom(map.stringList("aListOfStrings")));
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
		assertEquals(BAND, map.stringList("nothing").orElseGet(Throw.forStringList()));
	}

	@Test
	public void testListOr() {
		Iterable<String> x = MapWrapper.wrap(MAP).stringList("axListOfStrings")
				.orElse(list("hey"));
		assertEquals(list("hey"), x);
	}

	@Test(expected = MappingException.WrongType.class)
	public void testBadList() {

		MapWrapper m = MapWrapper.wrap(map("badlist", list("A", 2)));
		m.stringList("badlist").toList();
	}

	@Test
	public void testTransformFilterOut() {

		MapWrapper m = MapWrapper
				.wrap(map("badlist", list("A", 1, "B", 2, 0.0, 3)));

		List<String> badlist = m.stringList("badlist")
				.toList(Include.ofClass(String.class));

		assertEquals(badlist, list("A", "B"));
	}
}
