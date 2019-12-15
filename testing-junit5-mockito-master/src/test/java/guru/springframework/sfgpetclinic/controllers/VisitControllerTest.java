package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy
    PetMapService petService;

    @InjectMocks
    VisitController visitController;
    final Long id = 1L;

    @Test
    void loadPetWithVisit() {
//        given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(id);
        petService.save(pet);
        given(petService.findById(anyLong())).willCallRealMethod();

//        when
        Visit visit = visitController.loadPetWithVisit(id, model);

//        then
        assertNotNull(visit);
        assertNotNull(visit.getPet());
        assertEquals(id, visit.getPet().getId());
        verify(petService, times(1)).findById(anyLong());
    }
}