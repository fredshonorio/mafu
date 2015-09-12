
MAFU
====
MAps For hUmans
----

MAFU provides several somewhat lazy and somewhat functional methods of accessing (reading from, not writing to) maps in Java 8.
Due to the dynamic nature of this structure, the contained types are not known at compile time,
so handling maps (json object trees) in Java eventually becomes a mess of casts and checks.
It arose from the need to parse unruly json so it's mostly shaped by that.
It's has no dependencies.

Any improvement or debates regarding readability or expressiveness are welcome. Some names still feel weird.

Check [test directory](https://github.com/fredshonorio/mafu/tree/master/src/test/java/com/fredhonorio/mafu) for examples.

# Usage

Usage is as follows:
## Basics

After a map is wrapped: `MapWrapper map = MapWrapper.wrap(nakedmap)`, members are accessed by requesting specific types:

```java
String name = map.string("name").get();
```

Accessors for primitive types (`string()`, `boolean()`, etc) return a [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html), so you can do:

```java
String name = map.string("name").orElse("empty name");
```

If the value does not exist or is not of the specified type, the `Optional` will be empty.
Suppliers of values and exceptions can also be used with `orElseGet` and `orElseThrow`.
 
## Maps of Maps

Get a java `Map` inside a MapWrapper by using `object()`, it wraps the `Map` in another MapWrapper. Such as:

```java
// Returns the inner map, wrapped
MapWrapper name = map.object("name");
String fullName = name.string("first").get() + name.string("last").get();
```

MapWrapper is both a `Map` and a map container that mimics `Optional<Map>`:

```
Map defaultName = ...// native map

// You can provide a concrete map alternative,
Map name = map.object("name").orElse(defaultName);

// provide another MapWrapper,
MapWrapper name = map.object("name").orElse(map.object("friend").object("name"));
```

## Lists
Lists are hard! To get a list of a specific type you use the `ListWrapper`:

```java
ListWrapper<String> names = map.stringList("names");

// Implements Iterable<T>
for (String name : names) {
    System.out.println("Hey " + name + "!");
}

// It also mimics Optional<Iterable<T>>
Iterable<String> names = map.stringList("names").orElse(Arrays.asList("mark", "joanne"));

// And can return a fully evaluated list
List<String> names = map.stringList("names").toList();
```

It does __not__ check if the contained values match. The next section shows ways to deal with this.

### Type safety

The problem with lists is that you can't know ahead of time if every element is of the declared type. So this can happen:

```java
// Assume json has the following contents: {"badlist": ["A", 2]}

MapWrapper m = MapWrapper.wrap(json);
ListWrapper<String> goodList = m.stringList("badlist");
```

However, once you iterate the list and get to `2` you'll get a `MappingException.WrongType` error because `2` is not a `String` even thought `stringList` returns a `ListWrapper<String>`. To alleviate this you can either catch
`MappingException.WrongType` or transform the elements of the list by passing a transformer to `toList`, like so:

```java
// Remove null elements and use the result of toString for the rest
Function<Object, Optional<String>> convertToString = (o) ->
	Optional.ofNullable(o).map(Object::toString);

List<String> safeList = map.stringList("unsafeList").toList(convertToString);
```

To create your own transformer simply create a `Function<Object, Optional<T>>` where `T` is the type contained.
Return `Optional.empty()` to exclude the value from the list.

A `Function` to filter out elements that are not of a certain class is provided:

```java
List<String> safeList = map.stringList("unsafeList").toList(Include.ofClass(String.class));
```

Nested lists (`map.listOflistsOflistsOfStrings`) are not supported, only lists of primitives and lists of objects.

### Lists of objects

Lists of objects are accessed by ```map.objectList()```. The behavior is consistent with that of regular lists, save some [Gotchas](#gotchas). To filter invalid objects in `toList()` use `toList(Include.objects())`. Check the source to implement different transformers.

# Gotchas

## Lists of objects

Using `orElse()` for lists of objects is awkward. On one hand you want it to be an `Iterable<MapWrapper>` so that you can use the elements directly:

```java
List<String> names = new LinkedList<String>();

for(MapWrapper person : map.objectList("persons")) {
    names.add(person.string("name"));
}
```

but it would be useful to have:

```java
Map alternative = // a native (non-wrapped) map

map.objectList("persons").orElse(alternative);
```

but that would mean objectList returns an `Iterable<Map>`. The ugly solution is to wrap the native map:

```java
map.objectList("persons").orElse(MapWrapper.wrap(alternative));
```

You can also import `wrap` statically to be a little more succinct:

```java
import static com.fredhonorio.mafu.MapWrapper.wrap;
...
MapWrapper m = wrap(alternative);
```

# TODO
* map, filter, flatMap
* lists as java Stream?
* Lazy alternative to `toList(transform)`
* Talk about number/long
* Implement checked exceptions? Is it worth it? For Primitive accessors and objects it can be done, but for lists it has to be a runtime exception.
* Distinguish between missing and wrong type? It'd be a bit of work and i'm not sure it would be helpful, in either case you're excepting a certain structure and the object does not match. 
* Provide generic accessor: `MapWrapper.getAny(Object key, Class<T> class) : Optional<T>`
* More tests for primitive lists
