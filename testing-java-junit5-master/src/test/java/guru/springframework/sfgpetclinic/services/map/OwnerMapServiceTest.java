package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Owner Map Service Test - ")
class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    PetTypeService petTypeService;
    PetService petService;



    @BeforeEach
    void setUp() {

        petTypeService = new PetTypeMapService();
        petService = new PetMapService();
        ownerMapService = new OwnerMapService(petTypeService, petService);
        System.out.println("First Before Each invoked");
    }



    @DisplayName("Verify Zero Owners")
    @Test
    void ownersAreZero() {

        int ownerCount = ownerMapService.findAll().size();
        assertEquals(0, ownerCount);
//        assertThat(ownerCount).isZero();
    }



    @DisplayName("Pet Type - ")
    @Nested
    class TestCreatePetTypes {

        @BeforeEach
        void setUp() {
            PetType petType = new PetType(1L, "Dog");
            PetType petType2 = new PetType(2L, "Cat");
            petTypeService.save(petType);
            petTypeService.save(petType2);
            System.out.println("Nested Before Each invoked");
        }



        @DisplayName("Test Pet Count")
        @Test
        void testPetCount() {
            int petTypeCount = petTypeService.findAll().size();
            assertNotEquals(0, petTypeCount);
//            assertThat(petTypeCount).isNotZero().isEqualTo(2);
        }

        @DisplayName("Save Owners Tests - ")
        @Nested
        class SaveOwnersTests {

            @BeforeEach
            void setUp() {
                ownerMapService.save(new Owner(1L, "Before", "Each"));
            }

            @DisplayName("Save Owner")
            @Test
            void saveOwner() {
//                This is block level only
                Owner owner = new Owner(2L, "Joe", "Buck");
                Owner savedOwner = ownerMapService.save(owner);
                assertNotNull(savedOwner);
//                assertThat(savedOwner).isNotNull();
            }

            @DisplayName("Save Owners Tests - ")
            @Nested
            class FindOwnersTests {

                @DisplayName("Find Owner")
                @Test
                void findOwner() {
                    Owner foundOwner = ownerMapService.findById(1L);
                    assertNotNull(foundOwner);
//                    assertThat(foundOwner).isNotNull();
                }

                @DisplayName("Find Owner Not Found")
                @Test
                void findOwnerNotFound() {
//                    This wont be found even tho' you saved it above because that map gets reseted, only Before Each.
//                    Beware of the test cycle!!!
                    Owner foundOwner = ownerMapService.findById(2L);
                    assertNull(foundOwner);
//                    assertThat(foundOwner).isNull();
                }
            }
        }
    }



    @DisplayName("Verify Still Zero Owners")
    @Test
    void ownersAreStillZero() {
        int ownerCount = ownerMapService.findAll().size();
        assertEquals(0, ownerCount);
//        assertThat(ownerCount).isZero();
    }
}