package fifteen;

public class JDK15 {

    /*
     * JEP 360: Sealed Classes (Preview) - A new type of class or interface with more control over which other classes or interfaces can extend or implement them.
     * TODO: See JDK 17 notes JEP 409 where this feature has been delivered.
     *
     * JEP 371: Hidden Classes (Closed/Delivered) - Allows for runtime creation of classes that are not discoverable. Intended to be used by frameworks that dynamically
     * generate classes at runtime, and use them indirectly through reflection.
     *
     * JEP 375: Pattern Matching for instanceof (Second Preview) - Introduce pattern matching for the instance of operator, allowing conditional extraction of components
     * from objects. Reduces boilerplate code allowing expressions to become more concise. Also see notes on pattern matching for switch in JDK 17 JEP 406.
     * TODO: See JDK 16 notes JEP 394 where this feature has been delivered.
     *
     * JEP 383: Foreign-Memory Access API (Second Incubator) - Provides an API for safe and efficient access to both Java heap and native memory.
     *
     * Note about Garbage Collector: Both ZGC (JEP 377) and Shenandoah (JEP 379) are no longer experimental. G1 is still the
     * default. To use ZGCn use following GC options : -XX:+UseZGC -Xmx<size> -Xlog:gc
     *
     * Other noteworthy changes in JDK 15:
     * - DatagramSocket API has been re-written, a needed pre-requisite for Project Loom. JEP 373.
     * - Cryptographic support for Edwards-Curve Digital Signature Algorithm. JEP 339.
     * - Nashorn JavaScript engine removed, with GraalVM and other VM taking its place. JEP 372.
     *
     */
    public JDK15() {

        // JEP 378: Text Blocks - Multiline String literals

        // Old
        String htmlOld =   "<html>\n" +
                        "    <body>\n" +
                        "        <p>Hello, world</p>\n" +
                        "    </body>\n" +
                        "</html>\n";

        String htmlNew = """
                      <html>
                          <body>
                              <p>Hello, world</p>
                          </body>
                      </html>
                      """;

        System.out.println(htmlOld);
        System.out.println(htmlNew);


    }
}
