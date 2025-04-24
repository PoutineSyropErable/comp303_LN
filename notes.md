# Abstract Decorators

For abstract decorators, just implement a decorator which have a no op. So
implement the interface, and for the method exposed by the interface, just do:
method() { return aElement.method(); }

Then you can extend the abstract decorator. Why do it, well, it's a boilerplate
investement. More boilerplate for the abstract decorator, but the specific
decorator will just have to do super(pElement) for the constructor, and
super.method() for the other. Kinda useless, but hey...

Only use is that some method can have a default implementation.
So you skip on the boilerplate of undecorated method.

==== It allows you to make the .aElement that you are decorating private, and it doesn't need to be protected

# Comparator and Itterator

| Interface       | Functional Interface? | Function Object? | Purpose                                 | Key Method(s)                   |
| --------------- | --------------------- | ---------------- | --------------------------------------- | ------------------------------- |
| `Comparator<T>` | ✅ Yes                | ✅ Yes           | Compares two objects externally         | `int compare(T o1, T o2)`       |
| `Comparable<T>` | ✅ Yes                | ✅ Yes           | Compares self to another object         | `int compareTo(T other)`        |
| `Iterator<T>`   | ❌ No                 | ❌ No            | Traverses elements, maintains state     | `boolean hasNext()`, `T next()` |
| `Iterable<T>`   | ❌ No                 | ❌ No            | Represents a collection you can iterate | `Iterator<T> iterator()`        |

Code:

```java
class MySpecialIterator<T> implements Iterator<T> {

    private T currentElement;
    private int currentElementIndex;

    private MySpecialCollection<T> data;
    public MySpecialIterator(MySpecialCollection<T> collection) {
        // Copy all.
        data = collection.deepcopy();

        currentElementIndex = 0;
        currentElement = data.getFirst();
        // Assuming a getFirst exist.

  }

    public bool hasNext() {
        // if can return current element
        // If null, can't return.

  }

    public T next() {
        T ret = currentElement;

        currentElement = currentElement.getNext(currentElementIndex);
        currentElementIndex++;

        return ret;


  }

}

class MySpecialCollection<T> implements Iterable<T>, Comparable<T> {

    ?Collection data;

    MySpecialIterator<T> iterator() {
        return MySpecialIterator(this);
  }

    Optional<T> getNext(int currentIndex) {
        // if there is a next element, returns it.
        // otherwise, returns null.

  }

    static Optional<T> getFirst() {
        // if there is a first, returns it, otherwise returns null;

        return Optional.of("#<First element>")
        // or
        return Optional.ofNullable("#<First element, but it might be null>")
        //or
        return Optional.empty() // first element is null;
  }


    // Other methods related to the collection.
    // add element, get element, remove

    // compare to doesn't go here. It goes for the special element.


    // Sort with comparable
    public static  <T extends Comparable<T>> void sort(SpecialCollection col) {
        // ^Return type is void, <T extends Comparable<T>> is just some information.
        // for the compiler about T's information.



        // current, afterCurrent;

        // if current.compareTo(after) > 0 , swap current and afterCurrent
        // in the collection

  }

    public static <T> void sort(SpecialCollection col, Comparator<T> comp ) {
        // current, afterCurrent;

        // if comp.compare(current, afterCurrent) > 0 , swap current and afterCurrent
        // in the collection

  }

    // sort with coolness comparator


}


class SpecialElement implements Comparable <SpecialElement> {
    int compareTo(T other) {

        // returns (this - other) {for ints}

        // returns <0 if this is before other (other is after this) (this < other)
        // returns >0 if this is after other (other is before this). (this > other)
        // returns 0 if this == other.

        // It won't allow you to use the <, <=, >, >= operators.
        // No operator overloading
        // So you'd need wrappers for isLessThen, isBiggerThen, isBiggerEqual, isLessEqual

  }


}

class CoolnessComparator<T> implements Comparator<T> {

    int compare(T o1, T o2) {
        // returns <0 if o1 is before o2 (o2 is after o1)
        // returns >0 if o1 is after o2 (o2 is before o1).
        // returns (o1 - o2) {for ints}


        // equal to o1.compareTo(o2)
        // if same comparaison metric

  }
}

```

====

# Java optionals:

```Java

Optional<String> some = Optional.of("hello");       // ✅ non-null
Optional<String> maybeNull = Optional.ofNullable(null); // ✅ maybe-null
Optional<String> empty = Optional.empty();           // ✅ explicitly empty


Optional<String> name = Optional.of("François");
name.ifPresent(n -> System.out.println("Hello " + n));


String result = name.orElse("Default");
String lazy = name.orElseGet(() -> expensiveDefault());
String sure = name.orElseThrow();

```

# Java functional interface:

| Interface Name        | Return Type | Method Signature | Package              |
| --------------------- | ----------- | ---------------- | -------------------- |
| `Function<T, R>`      | `R`         | `apply(T)`       | `java.util.function` |
| `BiFunction<T, U, R>` | `R`         | `apply(T, U)`    | `java.util.function` |
| `Consumer<T>`         | `void`      | `accept(T)`      | `java.util.function` |
| `Supplier<T>`         | `T`         | `get()`          | `java.util.function` |
| `Predicate<T>`        | `boolean`   | `test(T)`        | `java.util.function` |
| `UnaryOperator<T>`    | `T`         | `apply(T)`       | `java.util.function` |
| `BinaryOperator<T>`   | `T`         | `apply(T, T)`    | `java.util.function` |
| `Runnable`            | `void`      | `run()`          | `java.lang`          |

# ✅ Java Functional Methods Cheat Sheet

## 🔁 Core Functional Methods (Beyond `map()` and `flatMap()`)

| Method                            | Input Type                    | Output Type                | Description                                  | Available In       |
| --------------------------------- | ----------------------------- | -------------------------- | -------------------------------------------- | ------------------ |
| `map(f: T → R)`                   | Regular function              | `Optional<R>`, `Stream<R>` | Transforms value                             | Optional, Stream   |
| `flatMap(f: T → Optional<R>)`     | Optional-returning function   | `Optional<R>`, `Stream<R>` | Flattens nested optionals or streams         | Optional, Stream   |
| `filter(f: T → boolean)`          | Predicate                     | `Optional<T>`, `Stream<T>` | Keeps value if condition passes              | Optional, Stream   |
| `peek(f: T → void)`               | Side-effect consumer          | `Stream<T>`                | Debug/log each value, doesn't alter stream   | Stream             |
| `orElse(T)`                       | Value                         | `T`                        | Returns value if present, else fallback      | Optional           |
| `orElseGet(Supplier<T>)`          | Supplier                      | `T`                        | Lazily compute fallback                      | Optional           |
| `orElseThrow()`                   | (none)                        | `T` or throws              | Throws `NoSuchElementException` if empty     | Optional           |
| `orElseThrow(Supplier)`           | Exception supplier            | `T` or throws              | Throws custom exception                      | Optional           |
| `or(Supplier<Optional<T>>)`       | Supplier of fallback optional | `Optional<T>`              | Fallback optional                            | Optional           |
| `ifPresent(Consumer<T>)`          | Consumer                      | `void`                     | Executes block if value present              | Optional           |
| `ifPresentOrElse(...)`            | Consumer + Runnable           | `void`                     | Handles both present and empty               | Optional (Java 9+) |
| `distinct()`                      | (none)                        | `Stream<T>`                | Removes duplicate elements                   | Stream             |
| `sorted()` / `sorted(Comparator)` | (none) / comparator           | `Stream<T>`                | Sorts elements                               | Stream             |
| `limit(n)`                        | int                           | `Stream<T>`                | Truncates after n elements                   | Stream             |
| `skip(n)`                         | int                           | `Stream<T>`                | Skips the first n elements                   | Stream             |
| `collect(...)`                    | Collector                     | Varies                     | Terminal operation to gather into collection | Stream             |
| `reduce(...)`                     | Accumulator                   | Optional<T> / T            | Combines stream elements into one            | Stream             |
| `anyMatch(Predicate)`             | Predicate                     | boolean                    | True if any element matches                  | Stream             |
| `allMatch(Predicate)`             | Predicate                     | boolean                    | True if all elements match                   | Stream             |
| `findFirst()` / `findAny()`       | (none)                        | `Optional<T>`              | Retrieves first/any value                    | Stream             |

---

## 🔁 Optional vs Stream Example

```java
Optional<String> name = Optional.of("Alice");
Optional<Integer> length = name.map(String::length); // Optional[5]

Stream<String> names = Stream.of("Alice", "Bob");
Stream<Integer> lengths = names.map(String::length); // Stream of 5, 3

```

```java
// collect a list of optional

List<Optional<T>> optionals = ...;

List<T> results = optionals.stream()
    .filter(Optional::isPresent)       // keep only non-empty optionals
    .map(Optional::get)                // unwrap the values
    .collect(Collectors.toList());     // collect into a new list

```

# Java Lambdas:

```java

// different way of creating a java function
Predicate<Card> blackCardFilter =
(Card card) -> card.getSuit().getColor() == Suit.Color.BLACK;

Predicate<Card> blackCards = (Card card) ->
{ return card.getSuit().getColor() == Suit.Color.BLACK; }

Predicate<Card> blackCardFilter =
(card) -> card.getSuit().getColor() == Suit.Color.BLACK;

Predicate<Card> blackCardFilter =
card -> card.getSuit().getColor() == Suit.Color.BLACK;


Supplier<Integer> get5 = () -> 5;


// Java lambdas (Anonymous functions)

//  ======== Class (Static) methods =============
cards.removeIf(card -> CardUtils.hasBlackSuit(card));
cards.removeIf(CardUtils::hasBlackSuit);

//  ======== Instance methods =============
cards.removeIf(card -> deck.topSameColorAs(card));
cards.removeIf(deck::topSameColorAs)
// Works for 0 or many arguments


// Using a predicate
cards.removeIf(blackCardFilter)


```

# Java FlapMap vs Map:

| Feature      | `map()`                         | `flatMap()`                                        |
| ------------ | ------------------------------- | -------------------------------------------------- |
| **Input**    | Function: `T → R`               | Function: `T → Optional<R>`                        |
| **Output**   | `Optional<R>`                   | `Optional<R>`                                      |
| **Use When** | You have a regular value result | You already return an `Optional` inside the lambda |
| **Avoids**   | Nested `Optional<Optional<R>>`  | ✅ Yes                                             |

====
Map:
if f: T->R

T.map(f: T -> R) --> R
Optional<T>.map (f: T -> R) --> Optional(R)

====
FlatMap

if f: T -> Optional<R>
Then,

Optional<T>.flatmap(f: T -> Optional<R>) --> Optional<R>
T.flatmap(f: T -> Optional<R>) --> Optional<R>

So this is for functions of

====
if use flatmap when
f: T->R, compile time error

if use map whne
f: T->Optional<R>

optional nesting: Optional<Optional<R>>

# ✅ JUnit & Unit Testing Cheat Sheet

## 📦 1. JUnit Basics (5.2, 5.5, 5.6)

### 💡 Key Annotations

| Annotation     | Purpose                                     |
| -------------- | ------------------------------------------- |
| `@Test`        | Marks a test method                         |
| `@BeforeEach`  | Runs before every test method               |
| `@AfterEach`   | Runs after every test method                |
| `@BeforeAll`   | Runs once before all tests (must be static) |
| `@AfterAll`    | Runs once after all tests (must be static)  |
| `@Disabled`    | Skips the test                              |
| `@DisplayName` | Custom name for the test                    |

---

### ⚖️ Assertions (`org.junit.jupiter.api.Assertions`)

| Assertion                                  | Purpose                         |
| ------------------------------------------ | ------------------------------- |
| `assertEquals(expected, actual)`           | Checks equality                 |
| `assertTrue(condition)`                    | Checks that condition is true   |
| `assertFalse(condition)`                   | Checks that condition is false  |
| `assertNull(value)`                        | Asserts the value is null       |
| `assertNotNull(value)`                     | Asserts the value is not null   |
| `assertThrows(Exception.class, () -> ...)` | Verifies an exception is thrown |
| `assertAll(...)`                           | Groups multiple assertions      |
| `assertTimeout(Duration, Executable)`      | Fails if exceeds time limit     |

---

# 🔍 Java Reflection API Cheat Sheet

## 📦 Class-Level Methods (`java.lang.Class`)

| Method             | Description                                    |
| ------------------ | ---------------------------------------------- |
| `getName()`        | Fully qualified class name                     |
| `getSimpleName()`  | Class name without package                     |
| `getPackage()`     | Gets the package object                        |
| `getSuperclass()`  | Gets the superclass class                      |
| `getInterfaces()`  | Interfaces implemented by the class            |
| `getModifiers()`   | Returns class modifiers (use with `Modifier`)  |
| `isInterface()`    | Checks if it's an interface                    |
| `isEnum()`         | Checks if it's an enum                         |
| `newInstance()` ⚠️ | Creates a new instance (deprecated in Java 9+) |

---

## 🧱 Field Methods (`java.lang.reflect.Field`)

| Method                 | Description                                  |
| ---------------------- | -------------------------------------------- |
| `getDeclaredFields()`  | All fields declared in class (incl. private) |
| `getFields()`          | Only public fields (incl. inherited)         |
| `getName()`            | Gets the field name                          |
| `getType()`            | Gets the field's type                        |
| `get(Object obj)`      | Gets field value from object                 |
| `set(Object obj, val)` | Sets field value on object                   |
| `setAccessible(true)`  | Bypasses access control                      |
| `isSynthetic()`        | Checks if field was generated by compiler    |

## 🔁 Method Methods (`java.lang.reflect.Method`)

| Method                 | Description                                   |
| ---------------------- | --------------------------------------------- |
| `getDeclaredMethods()` | All methods declared in class (incl. private) |
| `getMethods()`         | Only public methods (incl. inherited)         |
| `getName()`            | Gets the method name                          |
| `getParameterTypes()`  | Gets parameter types                          |
| `getReturnType()`      | Gets return type                              |
| `getModifiers()`       | Gets method modifiers                         |
| `invoke(obj, args...)` | Invokes method on object with arguments       |
| `setAccessible(true)`  | Allows access to private methods              |

## 🏗️ Constructor Methods (`java.lang.reflect.Constructor`)

| Method                      | Description                               |
| --------------------------- | ----------------------------------------- |
| `getDeclaredConstructors()` | All constructors declared (incl. private) |
| `getConstructors()`         | Only public constructors                  |
| `getParameterTypes()`       | Gets constructor parameter types          |
| `newInstance(args...)`      | Creates new instance using constructor    |
| `setAccessible(true)`       | Allows access to private constructors     |

## 🏷️ Annotation Methods

| Method                          | Description                     |
| ------------------------------- | ------------------------------- |
| `getAnnotations()`              | All annotations on element      |
| `getAnnotation(Class<A>)`       | Gets a specific annotation      |
| `isAnnotationPresent(Class<A>)` | Checks if annotation is present |

## 🔐 Common: Access Control

| Method                | Description                                                                |
| --------------------- | -------------------------------------------------------------------------- |
| `setAccessible(true)` | Used with fields/methods/constructors to allow access to `private` members |

## Reflections Example

```java

Class<Card> cardClass1 = Card.class
Class<?> cardClass2 = card.getClass()
// ? is a wildcard, meaning any class. Because if we have Card class, then why use an instance


// if you have only the name:

try {
    String fullyQualifiedName = "cards.Card";
    // cards is the package name.
    Class<Card> cardClass = (Class<Card>) Class.forName(fullyQualifiedName);
} catch(ClassNotFoundException e) {
    e.printStackTrace();
}


// Creating a new instance
try {
    Card card1 = Card.get(Rank.ACE, Suit.CLUBS);
    Constructor<Card> cardConstructor = Card.class.getDeclaredConstructor(Rank.class, Suit.class);
    cardConstructor.setAccessible(true);
    Card card2 = cardConstructor.newInstance(Rank.ACE, Suit.CLUBS);
    System.out.println(card1 == card2);
} catch (ReflectiveOperationException e) {
    e.printStackTrace();
}


try {
    Card card = Card.get(Rank.TWO, Suit.CLUBS);
    Field rankField = Card.class.getDeclaredField("aRank");
    rankField.setAccessible(true);
    rankField.set(card, Rank.ACE);
    System.out.println(card);
} catch (ReflectiveOperationException e) {
    e.printStackTrace();
}


// getDeclaredMethod("Method name", ClassType(Method arg1), ClassType(method arg2), ... )
// getDeclaredMethod("Method name", ClassType(Method arg1), ClassType(method arg2), ClassType(Method arg3), ... )
// getDeclaredMethod("Method name", Integer.class, float.class, Banana.class, ... )
// Same for get Declared constructor


// Types:
// Field, Constructor<T>, Method.
// Type.class.getDeclared#X("name", if args: Type(arg1).class, Type(arg2).class, ... __VARGS__ = *args)


Testing private methods
    public class TestFoundationPile {
        private FoundationPile aPile = new FoundationPile();

        // Private method getPreviousCard:
        private Optional<Card> getPreviousCard(Card pCard) {
        try {
            Method method = FoundationPile.class.getDeclaredMethod("getPreviousCard", Card.class);
            method.setAccessible(true);
            return (Optional<Card>) method.invoke(aPile, pCard);
        }
        catch (ReflectiveOperationException exception) {
            fail();
            return Optional.empty();
        }
    }

    @Test
    public void testGetPreviousCard_empty() {
        assertFalse(  getPreviousCard( Card.get(Rank.ACE, Suit.CLUBS) ).isPresent()  );
    }
}

```

# Exmaple of tests:

```java

@Test
public void testPeek_Empty() {
    assertThrows(EmptyStackException.class, () -> aPile.peek());
}
// Don't write test for stuff that has



@Test
void testWithdrawReducesBalance() {
    // Arrange
    Account acc = new Account(100);

    // Act
    acc.withdraw(30);

    // Assert
    assertEquals(70, acc.getBalance());
}

```

## 🧪 2. Unit Testing Principles (5.1, 5.5, 5.6, 5.7)

### ✅ Good tests are:

- Fast
- Independent
- Deterministic
- Repeatable
- Readable

# Liskov Substitution Principle (LSP)

• Per LSP, methods of a subclass:
• cannot have stricter preconditions;
• cannot have less strict postconditions;
• cannot take more specific types as parameters;
• cannot make the method less accessible (e.g., public -> protected);
• cannot throw more checked exceptions; and
• cannot have a less specific return type.
• The last four are automatically checked by the compil

# Law of Demeter

• Code in a method should only access:
• the instance variables of its implicit parameter (this);
• the arguments passed to the method;
• any new object(s) created within the method;
• (if need be) globally available objects.
• Max delegation chain length:
| Inside the class: 2
| Outside : 1

# Access modifier:

## 📋 Summary Table

| Modifier              | Class | Package | Subclass | World (everyone) |
| --------------------- | :---: | :-----: | :------: | :--------------: |
| `private`             |  ✅   |   ❌    |    ❌    |        ❌        |
| default (no modifier) |  ✅   |   ✅    |    ❌    |        ❌        |
| `protected`           |  ✅   |   ✅    |    ✅    |        ❌        |
| `public`              |  ✅   |   ✅    |    ✅    |        ✅        |

## Final keyword

    Final has a different meaning for fields, methods, classes:
    • A final field cannot be assigned a value more than once.
    • A final method cannot be overridden.
    • A final class cannot be inherited

---

# Todo:

Prototype, Clone, Gui, event, Reflection

# Anti pattern

| Anti P Name          | Explanation                                                                  |
| -------------------- | ---------------------------------------------------------------------------- |
| appropriate Intimacy | Class uses another’s internal state Delegate behavior, enforce encapsulation |
| Temporary Field      | Field not always needed Move to local var or extract class                   |
| Message Chain        | Long access chains (a.b().c().d()) Add intermediate methods, Law of Demeter  |
| Long Method          | Method does too much, hard to follow Extract smaller methods, clarify logic  |

More generally:

# ⚠️ Java Design Anti-Patterns Cheat Sheet

| Anti-Pattern               | Reference | Description                                                                                              |
| -------------------------- | --------- | -------------------------------------------------------------------------------------------------------- |
| **Primitive Obsession**    | 2.2       | Overusing primitive types instead of small objects for concepts (e.g. using `String` for phone numbers). |
| **Inappropriate Intimacy** | 2.5       | One class accessing the private details of another too frequently; tight coupling.                       |
| **Switch Statement**       | 3.4       | Using many `switch`/`if` statements to control logic instead of polymorphism.                            |
| **Speculative Generality** | 4.4       | Writing general-purpose code "just in case", before it's needed.                                         |
| **Temporary Field**        | 4.4       | Fields that are only used in certain circumstances, often left `null`.                                   |
| **Long Method**            | 4.6       | A method that is excessively long and hard to read or test.                                              |
| **God Class**              | 6.1       | A class that does too much — centralizes all functionality and logic.                                    |
| **Message Chain**          | 6.9       | A sequence of method calls like `a.getB().getC().doSomething()`; violates Law of Demeter.                |
| **Pairwise Dependencies**  | 8.1, 8.2  | Classes/modules that require knowledge of each other’s internal workings or lifecycle.                   |

---

# Prototype

## when

    Object creation is expensive (e.g. loading data, network requests)

    You need many similar objects with small variations

    You want to avoid subclassing factories

    You need to preserve state or structure in co

## how

    You define a base interface or abstract class with a clone() method.

    Concrete classes implement clone() to duplicate themselves.

    Clients call clone() on a prototype to get a new instance.

```java

Circle original = new Circle();
original.color = "red";
original.radius = 10;

Circle copy = original.clone();
copy.radius = 20;

```

# Clone

Make all class in the class heirachy implement clonable interface.
Recursively. Use constructor or cloning

```java
public MemorizingDeck clone() {
    MemorizingDeck clone = (MemorizingDeck) super.clone();
    clone.aDrawnCards = new CardStack(aDrawnCards);
    return clone;
}


# super(args) : constructor
# super.method(args) : methods
# can't super for static methods

```

# Object Diagram:

```

| objectName: ObjectType                |
-----------------------------------------
|   <fields>                            |
|    aggragetedFieldNName = ----------- | ----> | :Type        | # annonymous
|                                       |       ------------------
|                                       |       | Fields = ... |
|    primitiveField:String = "Value"    |    # because name already said.

```

---

# Sequence diagrams:

Like object diagram

```
| Object |
   |

   |

(--- horizontal)

Rectangle ________> Calls (full line)

<------------ return value. (doted line)


# Top: Start of the code.
# Bottom, end of the method code.

wizardPlayer.getActiveItemMO()

| client |   | Wizard Player|    | Item |
   |-             |-                 |-
   R              R                  R
   R              R_getActiveItemMO_>R
   R              R <-------         R
   R   <-----     R                  R
   R              R                  R
   R              R                  R
   R              R                  R



```
