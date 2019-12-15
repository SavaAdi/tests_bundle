package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.services.ModelRepeatedTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

public class PersonRepeatedTests implements ModelRepeatedTests {

    @RepeatedTest(value = 10, name = "{displayName} :: repetition {currentRepetition} of {totalRepetitions}")
    @DisplayName("My repeated Test")
    void myRepeatedTest() {
//        TODO
        System.out.println("I am a repeated test");
    }

    @RepeatedTest(5)
    void DIrepeatedTest(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println(testInfo.getDisplayName() + ": "  +repetitionInfo.getCurrentRepetition() );

    }


    @RepeatedTest(value = 5, name = "{displayName} :: repetition {currentRepetition} | {totalRepetitions}")
    @DisplayName("My assignment repeated Test")
    void myAssigmentRepeated() {

    }
}
