package seventeen;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * JEP 356: Enhanced Pseudo-Random Number Generators - Provides a collection of new interfaces and implementations for pseudorandom number generation. A new interface, RandomGenerator
 * has been created to supply a uniform API for all existing and new PRNG implementations. The four new interfaces are:
 * - SplittableGenerator to allow creating a new instance of RandomGenerator from an existing one that will produce statistically independent results.
 * - JumpableGenerator to jump ahead a moderate number of draws.
 * - LeapableGenerator to jump ahead a large number of draws.
 * - ArbitraryJumpableGenerator extends LeapableGenerator allowing for an arbitrary jump distance.
 *
 * A new RandomGeneratorFactory class has also been provided to construct instances of RandomGenerator implementations. This factory uses the ServiceLoader.Provider to register new implementations.
 *
 */
public class JEP356 {

    public JEP356() {

        // Generating random numbers using java.util.Random directly
        Random random = new Random();
        int randInt = random.nextInt();

        // The subclass java.util.concurrent.ThreadLocalRandom can be used to create a PRNG for better performance in a multithreaded environment.
        // The subclass java.security.SecureRandom can be used as a cryptographically strong PRNG where security is a higher concern.
        // A separate class java.util.SplittableRandom can be used to produce statistically independent sequences of values than of those from other instances.

        // The new API encompasses these implementations, along with several other generator implementations.

        // Provide a stream of all available generator factories:
        RandomGeneratorFactory.all()
                .sorted(Comparator.comparing(RandomGeneratorFactory::name))
                .forEach(factory -> System.out.printf("%s, %s, %s, %s.%n", factory.name(), factory.group(), factory.isJumpable(), factory.isSplittable()));

        // Locate factory by RandomGeneratorFactory properties:
        RandomGenerator randomGenerator = RandomGeneratorFactory.all()
                .filter(RandomGeneratorFactory::isSplittable)
                .findAny()
                .map(RandomGeneratorFactory::create)
                .orElse(RandomGeneratorFactory.getDefault().create());

        // Now in Java 17, in most cases we should no longer create direct instances of Random, rather we should use the RandomGenerator interface:
        RandomGenerator generator = RandomGenerator.getDefault(); // no requirements for specific generator implementation
        RandomGenerator xGenerator = RandomGenerator.of("Xoroshiro128PlusPlus"); // get specific implementation (new Xoroshiro generators)

        // In multithreaded environments, we can still use the legacy Random or SecureRandom, but we can now also split a new instance from an existing one:
        List<String> stringList = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newCachedThreadPool();

        RandomGenerator.SplittableGenerator generator1 = RandomGeneratorFactory
                .<RandomGenerator.SplittableGenerator>of("L128X256MixRandom")
                .create();

        generator1.splits(5).forEach(sg -> executorService.submit(() -> stringList.add(String.format("Random number up to 100: %s.", sg.nextInt(100)))));

        stringList.forEach(System.out::println);

        // Using splittable in this way means that multiple generator instances will produce unique values.

    }
}
