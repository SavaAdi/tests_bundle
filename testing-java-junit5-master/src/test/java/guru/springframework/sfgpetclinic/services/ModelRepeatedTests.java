package guru.springframework.sfgpetclinic.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;

@Tag("repeated")
public interface ModelRepeatedTests {

    @BeforeEach
    default void beforeEachConsoleOutput(TestInfo testInfo, RepetitionInfo repetitionInfo){
//        It won't see repetition info for all methods because it won't exist for non-repeated tests.
        System.out.println("Running Test - " + testInfo.getDisplayName() + " - " + repetitionInfo.getCurrentRepetition());
    }
}
