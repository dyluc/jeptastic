package fifteen;

/**
 * JEP 384: Records (Second Preview) - Containers for immutable data that reduce boilerplate while still allowing default behaviours to be overridden.
 *
 * Creating a record will generate many standard members automatically, making code less verbose and easier to debug:
 * - Each component inside the header of the record definition, has a public accessor method, and a private final field of the same type.
 * - A canonical constructor with a signature matching the record header, assigning each private field to its related argument.
 * - Implementations for .toString(), .equals() and .hashCode().
 */
public class JEP384 {

    private enum Gender { MALE, FEMALE }


    private record Person(String name, int age, Gender gender){

        public Person { // override default behaviour
            if(age < 0)
                throw new IllegalArgumentException("impossible, you must be some kind of alien");
        }

        public String name() { // override default behaviour
            return "name -> " + name;
        }

    }

    public JEP384() {
        // Construct a record.
        Person person = new Person("Dylan", 21, Gender.MALE);
        String name = person.name();
        int age = person.age();
        Gender gender = person.gender();

        System.out.printf("%s, %d, %s%n", name, age, gender.name());
        System.out.println(person); // .toString()
    }
}
