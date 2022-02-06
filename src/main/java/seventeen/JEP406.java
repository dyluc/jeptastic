package seventeen;

import java.util.Arrays;

import seventeen.JEP409.*;

/**
 * JEP 406: Pattern Matching for switch (Preview) - Allow pattern matching for switch statements and expressions, by extending case labels to include patterns rather than just constants,
 * and introduce 2 new patterns; guarded and parenthesized.
 *
 * Switch statements and expressions up until now worked with constants, so a case label would be selected based on an equality check. Now, with labels that accept patterns, selection is
 * determined using pattern matching instead.
 *
 */
public class JEP406 {

    public void jep406(Object obj, Instrument instrument, Object obj2, Object nullObject) {

        // New switch with pattern case labels. Selector expression types, on top of primitive, wrapper and enum types, can now also include any reference type.
        switch(obj) {
            case null -> System.out.println("Object is null");
            case String s -> System.out.printf("Object is a String %s.%n", s);
            case Gender gender -> System.out.printf("Object is a Gender %s.%n", gender.name());
            case int[] arr -> Arrays.stream(arr).forEach(System.out::println);
            case OnlyChild child -> System.out.printf("Object is an OnlyChild %s.%n", child.name());
            default -> System.out.println("Unidentified Object!");
        }

        // A pattern label can dominate another label. For example, Guitar g would be dominated by the previous patter Instrument i because all values that match Guitar g would
        // first match Instrument i. (The following would actually not compile, ensuring a switch block containing only type patterns must appear in subtype order).
//        switch(obj) {
//            case Instrument i -> System.out.println("Object is an Instrument");
//            case Guitar g -> System.out.println("Object is a Guitar");
//            default -> System.out.println("Unidentified Object!");
//        }

        // A switch expression requires all values of the selector expression to be handled in the switch block. Often this means providing a default clause to handle all other types
        // not handled in previous case label, just like the above example. But with the introduction of sealed classes, the type coverage check of the switch statement takes into account
        // class hierarchy to determine completeness of the expression (similar to enum switches with all cases covered). As an example:

        switch(instrument) { // subtype ordering (complete switch block no default needed)
            case YamahaAltoSaxophone yamahaSax -> System.out.println("Instrument is a Yamaha Alto Saxophone.");
            case AltoSaxophone altoSax -> System.out.println("Instrument is an Alto Saxophone.");
            case Saxophone sax -> System.out.println("Instrument is a Saxophone.");
            case Guitar guitar -> System.out.println("Instrument is a Guitar.");
            case Instrument inst -> System.out.println("Instrument is an Instrument."); // total pattern (will cover all remaining cases)
        }

        // The stricter type checking is now an added benefit of both switch statements and expressions. The scope of pattern variables, just like pattern matching for instanceof uses
        // flow-scoping, where a pattern variable is only in scope where the pattern has definitely matched. Let's see some examples:

        // Okay
        switch(obj2) {
            case String s :
                if(s.length() > 5)
                    System.out.println("Object is a String with length > 5.");
                // no break (s is a String, execution falls through to default case)
            default:
                // s is not accessible here
                System.out.println("Unidentified Object!");
        }

        // Not okay (compile time error)
        switch(obj2) {
            case String s :
                if(s.length() > 5)
                    System.out.println("Object is a String with length > 5.");
                // no break (s is a String, execution falls through to a case label that declares a pattern variable, compile time error)
//            case Integer i :
//                System.out.println("Object is an Integer");
            default:
                // s is not accessible here
                System.out.println("Unidentified Object!");
        }

        // We can now provide a null case for a label so that we can match a selector expression value that is null. Note this can also match for total expressions
        // so if a selector expression evaluates to null, a null case label, or a total pattern case label will match, if there is no such label, a NullPointerException is
        // thrown. This way, traditional switch statements and expressions will still function in the same way.

        // "Object is null." will be printed
        switch(nullObject) {
            case null -> System.out.println("Object is null.");
            default -> System.out.println("Unidentified Object!");
        }

        // NullPointerException will be thrown
        switch(nullObject) {
            default -> System.out.println("Unidentified Object!");
        }

        // With the introduction of a null case label, you can now have
        switch(nullObject) {
            case null:
            case String s:
                System.out.println("Object is either null or a String.");
                break;
            default:
                System.out.println("Unidentified Object!");
        }
        // or
        switch(nullObject) {
            case null, String s -> System.out.println("Object is either null or a String.");
            default -> System.out.println("Unidentified Object!");
        }

        // Now switch case labels can support a combination of both a pattern and a boolean expression. These are known as guarded patterns.
        // Take the above example of checking if the object is a String and with a length > 5. It can now be written as:
        switch(obj2) {
            case String s && s.length() > 5 -> System.out.println("Object is a String with length > 5.");
            case String s -> System.out.println("Object is a String.");
            default -> System.out.println("Unidentified Object!");
        }


    }

    public JEP406() {
        jep406(Gender.FEMALE, new Guitar(), "Hello World", null);
    }
}
