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

Final has a different meaning for fields, methods, classes:
• A final field cannot be assigned a value more than once.
• A final method cannot be overridden.
• A final class cannot be inherited

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

| Interface Name        | Return Type | Method Signature |
| --------------------- | ----------- | ---------------- |
| `Function<T, R>`      | `R`         | `apply(T)`       |
| `BiFunction<T, U, R>` | `R`         | `apply(T, U)`    |
| `Consumer<T>`         | `void`      | `accept(T)`      |
| `Supplier<T>`         | `T`         | `get()`          |
| `Predicate<T>`        | `boolean`   | `test(T)`        |
| `UnaryOperator<T>`    | `T`         | `apply(T)`       |
| `BinaryOperator<T>`   | `T`         | `apply(T, T)`    |

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

## 🧪 2. Unit Testing Principles (5.1, 5.5, 5.6, 5.7)

### ✅ Good tests are:

- Fast
- Independent
- Deterministic
- Repeatable
- Readable

### 📄 Test structure:

```java
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
