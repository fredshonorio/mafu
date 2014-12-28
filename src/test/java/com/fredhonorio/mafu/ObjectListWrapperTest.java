package com.fredhonorio.mafu;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fredhonorio.mafu.functions.Include;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class ObjectListWrapperTest {

	// @formatter:off
	public static List<ImmutableMap<String, String>> BAND = ImmutableList.of(
			ImmutableMap.of("name", "harry"),
			ImmutableMap.of("name", "the potters")
    );
	// @formatter:on

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// @formatter:off
	public static Map MAP = ImmutableMap.copyOf(new HashMap() {
		private static final long serialVersionUID = 1L;
		{
			put("aListOfObjects", BAND);
			put("bad",
				ImmutableList.of(
					ImmutableMap.of("name", "harry"),
					2,
					ImmutableMap.of("name", "the potters"),
					3
				)
			);
		}
	});
	// @formatter:on

	@Test
	public void testSimple() {
		MapWrapper map = MapWrapper.wrap(MAP);
		assertEquals(BAND, Lists.newLinkedList(map.objectList("aListOfObjects")));

		List<MapWrapper> list = map.objectList("aListOfObjects").toList();
		assertEquals(ImmutableMap.of("name", "harry"), list.get(0));
		assertEquals(ImmutableMap.of("name", "the potters"), list.get(1));
	}

	@Test(expected = MappingException.MissingOrWrongType.class)
	public void testOr() {
		MapWrapper map = MapWrapper.wrap(MAP);

		// or() for lists of objects is awkward
		MapWrapper alternative = MapWrapper.wrap(ImmutableMap.of("woo", "wee"));

		Iterator<?> x = map.objectList("nothing").or(ImmutableList.of(alternative)).iterator();
		assertEquals(alternative, x.next());
		assertFalse(x.hasNext());

		assertEquals(BAND, Lists.newLinkedList(map.objectList("nothing").get()));
	}

	@Test
	public void testTransform() {
		MapWrapper map = MapWrapper.wrap(MAP);

		List<MapWrapper> x = map.objectList("bad").toList(Include.objects());
		assertEquals(ImmutableMap.of("name", "harry"), x.get(0));
		assertEquals(ImmutableMap.of("name", "the potters"), x.get(1));
	}
}
