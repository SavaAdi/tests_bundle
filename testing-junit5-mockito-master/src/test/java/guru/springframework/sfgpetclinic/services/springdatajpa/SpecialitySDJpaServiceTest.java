package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService specialtyService;

    @Test
    void findByIdBDDTest() {
//        given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

//        when
        Speciality foundSpecialty = specialtyService.findById(1L);
        assertThat(foundSpecialty).isNotNull();

//        then
        then(specialtyRepository).should(times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testDeleteByObject() {
//        given
        Speciality speciality = new Speciality();
//        when
        specialtyService.delete(speciality);
//        then
        then(specialtyRepository).should().delete(any(Speciality.class));
//        verify(specialtyRepository).delete(any(Speciality.class));
    }


    @Test
    void findByIdTest(){
        Speciality speciality = new Speciality();
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
        Speciality foundSpecialty = specialtyService.findById(1L);
        assertThat(foundSpecialty).isNotNull();
        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void deleteByIdBDDTest() {
//        given - none (it's optional to specify it)
//        when
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);
//        then
        then(specialtyRepository).should(times(2)).deleteById(1L);
//        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteById() {
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);
        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeastBDDTest() {
//        given

//        when
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

//        then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        specialtyService.deleteById(1L);
        verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void delete() {
        specialtyService.delete(new Speciality());
    }

    @Test
    void deleteBDDTest() {
//        when
        specialtyService.delete(new Speciality());

//        then
        then(specialtyRepository).should(only()).delete(any(Speciality.class));
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIdThrows() {
        given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("Boom"));
        assertThrows(RuntimeException.class, () -> specialtyService.findById(1L));
        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testDeleteBDD() {
//        if you method is a void type
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
        then(specialtyRepository).should().delete(any());
    }

    @Test
    void testSaveLambda() {
//        given
        final Long id = 1L;
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(id);
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

//        when
        Speciality returnedSpecialty = specialtyService.save(speciality);

//        then
        assertEquals(id, returnedSpecialty.getId());
    }

    @Test
    void testSaveLambdaNoMatch() {
//        given
        final Long id = 1L;
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not a match");

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(id);
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

//        when
        Speciality returnedSpecialty = specialtyService.save(speciality);

//        then
        assertNull(returnedSpecialty);
    }
}