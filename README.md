
# Dependency Resolver

## Overview

This project provides a dependency resolution algorithm that determines the full set of transitive dependencies for a group of items. The code takes as input a set of lines where the first token is the name of an item. The remaining tokens are the names of things that this first item depends on. Given the following input, we know that A directly depends on B and C, B depends on C and E, and so on.
```
A B C
B C E
C G
D A F
E F
F H
```

The algorithm uses this data to calculate the full set of dependencies.
The output of the program for the above input should look like:
```
A B C E F G H
B C E F G H
C G
D A B C E F G H
E F H
F H
```
Additionally we can also calculate inverse dependencies (i.e., determine the set of items that depend on each item). Here we do not ask the question does A depend on B, but instead we want to know where exactly B participates as a dependency? Again for our example that would be:
```
A D
B A D
C A B D
E A B D
F A B D E
G A B C D
H A B D E F
```

## Build
This is a Maven based project. So to build it and run the tests execute:
```
mvn clean install
```
To only run the tests:
```
mvn test
```
To skip running tests:
```
mvn clean install -DskipTests
```

## Dependencies
The project uses no dependencies. Only for testing it uses the latest major version of JUnit *(JUnit 5)*. I have used *IntelliJ IDEA 2016.3* as an IDE and can confirm that it has support for JUnit 5 tests. Jetbrains claim that there is support since 2016.2 but I had issues with that version so I upgraded.

## License
The content of this project itself is licensed under the [Apache License Version 2.0](http://www.apache.org/licenses) license.

Enjoy ;)

