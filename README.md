
# MAFU - MAps For hUmans

MAFU provides several somewhat lazy and somewhat functional methods of accessing (reading from, not writting to) maps in Java.
Due to the nature of this structure, the contained types are not known at compile time,
so handling maps (json object trees) in Java eventually becomes a mess of casts and checks. It arrose from the need to parse unrully json so it's mostly shapped for that.
It's only dependency is Google's Guava library.

## Usage
Usage is as follows:
### Basics

After a map is wrapped ```MapWrapper map = MapWrapper.wrap(nakedmap)``` members are accessed by requesting specific types:

```String name = map.string("name").get();```

Accessors for primitive types (like "string", "boolean", etc) return a guava Optional so you can:

```String name = map.string("name").or("empty name");```

Guava suppliers that throw an exception when the element is missing are used like so:

```
// Throws MappingException.MissingOrWrongType
String name = map.string("name").or(Throw.forString());
```

Or a checked exception:

```
// Throws CheckedMappingException.MissingOrWrongType
String name = map.string("name").or(ThrowChecked.forString());
```

__NOTE:__ Accessor methods for primiteves return `Optional.absent()` if the value does not exist or is not of the requested type.

### Maps of Maps

Accessing java ```Map```s inside a MapWrapper returns another MapWrapper. Such as:

```
// Returns the inner map, wrapped
MapWrapper name = map.object("name");

String fullName = name.string("first").get() + name.string("last").get();
```

MapWrapper is both a Map and a map container that mimicks `Optional<Map>`:
```
Map defaultName = ImmutableMap.of(
    "first", "John",
    "last", "Doe"
);

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
```

The problem with lists is that you can't know ahead of time if every element is of the declared type. So this can happen:
```
MapWrapper m = MapWrapper.wrap(
    ImmutableMap.of(
        "badlist", ImmutableList.of("A", 2)
	)
);

for (Object x : m.stringList("badlist"))
    System.out.println(x);

// prints:
// A
// 2
```
Even thought `stringList` returns a `ListWrapper<String>`. One solution is calling `toList()` which traverses the inner list and copies every element an immutable list while checking every element. This of course can be costly if the list is too large.

__NOTE:__ The accessor methods for list (`stringList()`, `objectList()`) return `Optional.absent()` if the value does not exist or is not a `List`, it does not check if the contained values match, __TODO__: LINK TO Gotchas.List.

## Gotchas
### Lists
__TODO__: describe processing list so it conforms to type signature.

### ThrowChecked
Although the constructor methods (forString, forBoolean, etc) declare ```throws CheckedMappingException.MissingOrWrongType``` the exception is actually thrown when the ```or()``` method calls the suppliers ```get()``` method. This just makes sure you have to catch the exception.

## TODO
* Checked exceptions
* Distinguish between missing and wrong type? It'd be a bit of work and i'm not sure it would be helpful, in either case you're excepting a certain structure and the object does not match. 
* Make MapWrapper/ListWrapper a supplier
* Provide generic accessor: MapWrapper.getAny(Object key, Class<T> class) : Optional<T>
* Finish lists
