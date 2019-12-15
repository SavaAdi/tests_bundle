package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest implements ModelTests {

    @Test
    void groupedAssertions() {
//         given
        Person person = new Person(1L, "Joe", "Thootar");

//         then
        assertAll("Test Props Set",
                () -> assertEquals("Joe", person.getFirstName()),
                () -> assertEquals("Thootar", person.getLastName()));
    }

    @Test
    void groupedAssertionsNotEquals() {
//         given
        Person person = new Person(1L, "Joe", "Thootar");

//         then
        assertAll("Test Props Set",
                () -> assertNotEquals("NotJoe", person.getFirstName(), "First Name Failed"),
                () -> assertNotEquals("OtherThootar", person.getLastName(), "Last Name Failed"));
    }


}