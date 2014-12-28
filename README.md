
# MAFU - MAps For hUmans

MAFU provides several somewhat lazy and somewhat functional methods of accessing (reading from, not writting to) maps in Java.
Due to the nature of this structure, the contained types are not known at compile time,
so handling maps (json object trees) in Java eventually becomes a mess of casts and checks. It arrose from the need to parse unrully json so it's mostly shapped for that.
It's only dependency is Google's Guava library.

Any improvement or debates regarding readability or expressivity are welcome.

## Usage
Usage is as follows:
### Basics

After a map is wrapped: `MapWrapper map = MapWrapper.wrap(nakedmap)`, members are accessed by requesting specific types:

```
String name = map.string("name").get();
```

Accessors for primitive types (`string()`, `boolean()`, etc) return a guava Optional, so you can do:

```
String name = map.string("name").or("empty name");
```

Guava suppliers that throw an exception when the element is missing can be used like so:

```
// Throws MappingException.MissingOrWrongType
String name = map.string("name").or(Throw.forString());
```

__NOTE:__ Accessor methods for primiteves return `Optional.absent()` if the value does not exist or is not of the requested type.

### Maps of Maps

Accessing a java `Map` inside a MapWrapper by using `object()`, it returns another MapWrapper. Such as:

```
// Returns the inner map, wrapped
MapWrapper name = map.object("name");
String fullName = name.string("first").get() + name.string("last").get();
```

MapWrapper is both a `Map` and a map container that mimicks `Optional<Map>`:
```
Map defaultName = // native map

// You can provide a concrete map alternative,
Map name = map.object("name").or(defaultName);

// provide another MapWrapper,
MapWrapper name = map.object("name").or(map.object("friend").object("name"));

// or both!
Map name = map.object("name").or(map.object("friend").object("name")).or(defaultName);
```

__NOTE:__ The accessor method for maps (`object()`) returns `Optional.absent()` if the value does not exist or is not a `Map`.

### Lists
Lists are hard! To get a list of a specific type you use the `ListWrapper`:
```
ListWrapper<String> names = map.stringList("names");

// Implements Iterable<T>
for (String name : names) {
    System.out.println("Hey " + name + "!");
}

// It also mimicks Optional<Iterable<T>>
Iterable<String> names = map.stringList("names").or(ImmutableList.of("mark", "joanne"));

// And can return a list
List<String> names = map.stringList("names").toList();
```

__NOTE:__ The accessor methods for list (`stringList()`, `objectList()`) returns `Optional.absent()` if the value does not exist or is not a `List`. It does __not__ check if the contained values match. Check the next section for ways to deal with this.

#### Safety

The problem with lists is that you can't know ahead of time if every element is of the declared type. So this can happen:
```
MapWrapper m = MapWrapper.wrap(
    ImmutableMap.of(
        "badlist", ImmutableList.of("A", 2)
	)
);

ListWrapper<String> goodList = m.stringList("badlist");
```
However, once you iterate the list and get to `2` you'll get a `MappingException.WrongType` error because `2` is not a `String` even thought `stringList` returns a `ListWrapper<String>`. To eleviate this you can either catch
`MappingException.WrongType` or transform the elements of the list by passing a transformer to `toList`, like so:
```

// Remove null elements and use the result of toString for the rest
Function<Object, Optional<String>> convertToString = new Function<Object, Optional<String>>() {
    @Override
	public Optional<String> apply(Object input) {

        if(input != null)
            return Optional.of(input.toString());

        return Optional.absent();
	}
};

List<String> safeList = map.stringList("unsafeList").toList(convertToString);

```
To create your own transformer simply create a `Function<Object, Optional<T>>` where `T` is the type contained. Return `Optional.absent()` to exclude the value from the list.


A `Function` to filter out elements that are not of a certain class is provided:
```
List<String> safeList = map.stringList("unsafeList").toList(Include.ofClass(String.class));
```

Nested lists (`map.listOflistsOflistsOfStrings`) is not supported, only lists of primitives and lists of objects.

#### Lists of objects

Lists of objects are accessed by ```map.objectList()```. The behavior is consistent with that of regular lists, save some Gotchas. To filter invalid objects in `toList()` use `toList(Include.objects())`. Check the source to extend this transformer.

## Gotchas

### Lists of objects

Using `or()` for lists of objects is awkward. On one hand you want it to be an `Iterable<MapWrapper>` so that you can use the elements directly:
```
List<String> names = new LinkedList<String>();

for(MapWrapper person : map.objectList("persons")) {
    names.add(person.string("name"));
}
```
but it would be useful to have:
```
Map alternative = // a native (non-wrapped) map

map.objectList("persons").or(alternative);
```
but that would mean objectList returns an `Iterable<Map>`. The ugly solution is to wrap the native map:
```
map.objectList("persons").or(MapWrapper.wrap(alternative));
```

## TODO
* Finish exception suppliers
* Lazy alternative to `toList(transform)`
* Talk about number/long
* Implement checked exceptions? Is it worth it? For Primitive accessors and objects it can be done, but for lists it has to be a runtime exception.
* Distinguish between missing and wrong type? It'd be a bit of work and i'm not sure it would be helpful, in either case you're excepting a certain structure and the object does not match. 
* Provide generic accessor: `MapWrapper.getAny(Object key, Class<T> class) : Optional<T>`
* More tests for primitive lists
