package sixteen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * JEP 394: Pattern Matching for instanceof - Introduce pattern matching for the instance of operator. This is now a finalized feature of the language.
 */
public class JEP394 {

    @AllArgsConstructor
    @Getter
    @Setter
    private static class Animal {
        private String name;
        private int age;
    }

    private static class Cat extends Animal {
        public Cat() {
            super("Chuck", 7);
        }
        public void meow() {
            System.out.println("Meow!");
        }

        @Override
        public boolean equals(Object obj) {
            // Without pattern matching.
            if(!(obj instanceof Cat))
                return false;
            Cat cat = (Cat) obj; // cast to cat
            return
                    cat.getName().equals(getName()) &&
                            cat.getAge() == getAge();
        }
    }

    private static class Dog extends Animal {
        public Dog() {
            super("Elmore", 2);
        }
        public void bark() {
            System.out.println("Woof!");
        }
        @Override
        public boolean equals(Object obj) {
            // With pattern matching.
            return (obj instanceof Dog dog) &&
                    dog.getName().equals(getName()) &&
                    dog.getAge() == getAge();
        }
    }

    public void jep394(Animal animal, Object object) {

        // Without pattern matching. The compiler can now reasonably determine that all cases are covered.
        if(animal instanceof Cat)
            ((Cat) animal).meow();
        else if(animal instanceof Dog)
            ((Dog) animal).bark();

        // Pattern - Consisting of a predicate and a set of pattern variables
        // Type Pattern - Consists of a predicate that specifies a type and a single pattern variable
        // The instanceof operator has now been extended to take a pattern and not just a type. So the above can become:
        if(animal instanceof Cat cat)
            cat.meow();
        else if(animal instanceof Dog dog)
            dog.bark();

        // Pattern variables use flow scoping, which means a pattern variable will only be in scope where the pattern has definitely matched.
        // For example:
        if(object instanceof String s && s.length() > 3) // The pattern variable s is in scope on the right side of the && operator.
            System.out.printf("Object is a String of length > 3. %s.", s); // as well as in the true block.

        // if(object instanceof String s || s.length() > 3) // The pattern variable s is NOT in scope on the right side of the && operator.
        //    System.out.printf("Object is a String of length > 3. %s.", s); // nor is it in the true block.

        // Flow scoping for pattern variables is also aware of when a statement can complete normally.
        // For example:
        if(!(animal instanceof Dog dog))
            return;

        // Here dog is in scope because the conditional statement completed normally. In other words, this is a dog only zone.
        dog.bark();

    }

    public JEP394() {
        jep394(new Dog(), "Hello World");
    }
}
