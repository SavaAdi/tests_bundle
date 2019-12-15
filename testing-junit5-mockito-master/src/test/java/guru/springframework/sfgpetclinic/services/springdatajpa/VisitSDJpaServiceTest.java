package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    Visit visit;

    @BeforeEach
    void setUp() {
        visit = new Visit();
    }

    @Test
    void findAll() {

        Set<Visit> visitSet = new HashSet<>();
        visitSet.add(visit);

        when(visitRepository.findAll()).thenReturn(visitSet);

        Set<Visit> foundVisits = service.findAll();
        verify(visitRepository).findAll();
        assertEquals(1, foundVisits.size());
    }

    @Test
    void findAllBDD() {
//      given
        Set<Visit> visitSet = new HashSet<>();
        visitSet.add(visit);
        given(visitRepository.findAll()).willReturn(visitSet);

//      when
        Set<Visit> foundVisits = service.findAll();
//      then
        then(visitRepository).should().findAll();
        assertEquals(1, foundVisits.size());
    }

    @Test
    void findById() {

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
        Visit foundVisit = service.findById(1L);
        verify(visitRepository).findById(anyLong());
        assertNotNull(foundVisit);
    }

    @Test
    void findByIdBDD() {
//      given
        given(visitRepository.findById(1L)).willReturn(Optional.of(visit));

//      when
        Visit foundVisit = service.findById(1L);
//      then
        then(visitRepository).should().findById(anyLong());
        assertNotNull(foundVisit);
    }

    @Test
    void save() {

        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        Visit savedVisit = service.save(visit);
        verify(visitRepository).save(any(Visit.class));
        assertNotNull(savedVisit);
    }

    @Test
    void saveBDD() {
//        given
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

//        when
        Visit savedVisit = service.save(visit);

//        then
        then(visitRepository).should().save(any(Visit.class));
        assertNotNull(savedVisit);
    }

    @Test
    void delete() {
        service.delete(visit);
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteBDD() {
//        when
        service.delete(visit);
//        then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(visitRepository).deleteById(anyLong());
    }

    @Test
    void deleteByIdBDD() {
//        when
        service.deleteById(anyLong());
//        then
        then(visitRepository).should().deleteById(anyLong());
    }


}