package com.fredhonorio.mafu;

import static com.fredhonorio.mafu.Immutable.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fredhonorio.mafu.functions.Include;

public class ObjectListWrapperTest {

	public static List<Map<String, String>> BAND = list(map("name", "harry"),
			map("name", "the potters"));

	@SuppressWarnings({ "rawtypes" })
	public static Map MAP = map("aListOfObjects", BAND, "bad",
			list(map("name", "harry"), 2, map("name", "the potters"), 3));

	@Test
	public void testSimple() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals(BAND, listFrom(map.objectList("aListOfObjects")));

		List<MapWrapper> list = map.objectList("aListOfObjects").toList();
		assertEquals(map("name", "harry"), list.get(0));
		assertEquals(map("name", "the potters"), list.get(1));
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testOr() {
		MapWrapper map = MapWrapper.wrap(MAP);

		// or() for lists of objects is awkward
		MapWrapper alternative = MapWrapper.wrap(map("woo", "wee"));

		Iterator<?> x = map.objectList("nothing").orElse(list(alternative)).iterator();
		assertEquals(alternative, x.next());
		assertFalse(x.hasNext());

		assertEquals(BAND, listFrom(map.objectList("nothing").get()));
	}

	@Test
	public void testTransform() {
		MapWrapper map = MapWrapper.wrap(MAP);

		List<MapWrapper> x = map.objectList("bad").toList(Include.objects());
		assertEquals(Immutable.map("name", "harry"), x.get(0));
		assertEquals(Immutable.map("name", "the potters"), x.get(1));
	}
}
