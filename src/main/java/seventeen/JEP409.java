package seventeen;

/**
 * JEP 409: Sealed Classes - Sealed classes and interfaces restrict which subclasses and interfaces can extend or implement them. This is now a finalized feature.
 *
 * The general goals of sealed classes were to provide a more declarative way of restricting how a superclasses' usage. This can be done with access modifiers such as
 * private, protected, etc, but with sealed classes you now have more control over which code is allowed to extend or implement your class/interface. This also will have
 * future implications for pattern matching.
 *
 * Subclasses must be within the same module or package (if inside an unnamed module) as the superclass. Alternatively, the subclasses can be defined within the body of
 * the sealed super class such that they become nested classes. In this situation, the permits clause is not needed and the compiler can infer the permitted subclasses
 * from the definition. For example:
 *
 * abstract sealed class Root { ...
 *     final class A extends Root { ... }
 *     final class B extends Root { ... }
 *     final class C extends Root { ... }
 * }
 *
 * All permitted subclasses of a superclass must also directly extend the sealed class, and must use a modifier to specify how it propagates the sealing. For example a subclass
 * can be:
 * - Declared final to prevent the class hierarchy from being further extended.
 * - Declared sealed to allow the class hierarchy to be extended further, but in a restrictive way by declaring a permits clause just like the superclass.
 * - Declared non-sealed to allow the class hierarchy to be extended further, but allowing any unknown subclass (note the superclass cannot prevent permitted subclasses
 * from doing this).
 *
 * The same can be done with interfaces.
 *
 * Java does not allow multiple class inheritance and records already extend java.lang.Record, so records can only implement interfaces, including those that are sealed.
 * Note that records are also implicitly final and so will seal off the class hierarchy.
 *
 * Something to note about how sealed classes relate to JEP 406 (Pattern matching for switch), is that with completely sealed off class hierarchy, the compiler knows all possible
 * subclass cases in a switch statements using type patterns. In this case, a default clause is not needed. See notes on JEP 406 for examples of this.
 */
public class JEP409 {
    public static abstract sealed class Instrument permits Guitar, Saxophone {}

    // Class hierarchy sealed off
    public static final class Guitar extends Instrument {}

    // Class hierarchy allowed to be extended by permitted subclass
    public sealed class Saxophone extends Instrument permits AltoSaxophone {}

    // Break sealed class hierarchy by allowing any class to extend it
    public non-sealed class AltoSaxophone extends Saxophone {}

    // Regular class definition
    public class YamahaAltoSaxophone extends AltoSaxophone {}

    public sealed interface Parent permits OnlyChild {}

    // Class hierarchy sealed off
    public record OnlyChild(String name) implements Parent {}

    public enum Gender { MALE, FEMALE }
}
