package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllerTests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("controller")
class IndexControllerTest implements ControllerTests {

    IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @Test
    @DisplayName("Test proper view name for index page")
    void index() {
        assertEquals("index", controller.index());
        assertEquals("index", controller.index(), () -> "Lambda message is only generated in case of failure, " +
                "use them when the error message is 'expensive'!");

    }

    @Test
    @DisplayName("Test exception handling")
    void oupsHandler() {

        assertThrows(ValueNotFoundException.class, () -> {
            controller.oupsHandler();
        });

//        assertTrue("notimplemented".equals(controller.oupsHandler()), () -> "This is some expensive" + "message to build" +
//                " for my test");
    }

    @Disabled("Test timeout")
    @Test
    void testTimeout() {

        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(400);
        });

        System.out.println("Im second");
    }

    @Disabled("Disabled timeout preemptively")
    @Test
    void testTimeoutPreempt() {

        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(400);
        });

        System.out.println("Im first");
    }

    @Test
    void testAssumptionTrue() {

        assumeTrue("ADI".equalsIgnoreCase(System.getenv("ADI_RUNTIME")));

    }

    @Test
    void testAssumptionTrueIsTrue() {
        assumeTrue("ADI".equalsIgnoreCase("ADI"));
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testMeOnWindows() {
        System.out.println("Im on windows");
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void testMeOnMac() {
        System.out.println("Run me on MAC");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testMeIfJava8() {
        System.out.println("This is java 8");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
    void testIfEnvVariableMatches() {
    }
}