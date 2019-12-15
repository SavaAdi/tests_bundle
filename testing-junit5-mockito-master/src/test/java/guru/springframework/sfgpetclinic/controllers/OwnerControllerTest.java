package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processFindFormMockAnswer() {
//        given
        Owner owner = new Owner(1L, "Joe", "Bucky");
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {
                    String name = invocation.getArgument(0);
                    List<Owner> ownerList = new ArrayList<>();
                    if(name.equals("%Bucky%")) {
                        ownerList.add(new Owner(1L, "Joe", "Bucky"));
                        return ownerList;
                    }

                    throw new RuntimeException("Invalid Argument");
                });
//        when
        String viewName = ownerController.processFindForm(owner, bindingResult, model);
//        then
        assertTrue(stringArgumentCaptor.getValue().equalsIgnoreCase("%Bucky%"));
        assertFalse(stringArgumentCaptor.getValue().equalsIgnoreCase("sdsfky%"));
        assertTrue("redirect:/owners/1".equals(viewName));
        System.out.println(stringArgumentCaptor.getValue());
    }

    @Test
    void processFindFormWildcardString() {
//        given
        Owner owner = new Owner(1L, "Joe", "Bucky");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

//        when
        String viewName = ownerController.processFindForm(owner, bindingResult, model);

//        then
        assertTrue(captor.getValue().equalsIgnoreCase("%Bucky%"));
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
//        given
        Owner owner = new Owner(1L, "Joe", "Bucky");
        List<Owner> ownerList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);
        InOrder inOrder = Mockito.inOrder(ownerService, model);
//        when
        String viewName = ownerController.processFindForm(owner, bindingResult, model);

//        then
        assertTrue(stringArgumentCaptor.getValue().equalsIgnoreCase("%Bucky%"));
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());

    }

    @Test
    void processCreationFormHasErrors() {
//        given
        Owner owner = new Owner(1L, "Jim", "Bobby");
        given(bindingResult.hasErrors()).willReturn(true);

//        when
        String viewName = ownerController.processCreationForm(owner, bindingResult);

//        then
        assertTrue(viewName.equalsIgnoreCase(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processCreationFormNoErrors() {
//        given
        Owner owner = new Owner(5L, "Jim", "Bobby");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);
//        when
        String viewName = ownerController.processCreationForm(owner, bindingResult);
//        then
        assertTrue(viewName.equalsIgnoreCase(REDIRECT_OWNERS_5));
    }
}