package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.samples.petclinic.web.OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    OwnerController ownerController;

//    Spring provides this via IoC Container
    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @AfterEach
    void tearDown() {
//        without this returnListOfOwnersTest and findOwnerOneResultTest will fail because clinicService is "dirty"
        reset(clinicService);
    }

    @Test
    void updateOwnerPostValidTest() throws Exception{
        mockMvc.perform(post("/owners/{ownerId}/edit", 1)
                    .param("firstName", "Robin")
                    .param("lastName", "Hood")
                    .param("address", "Nottingham 2nd Street")
                    .param("city", "Nottingham")
                    .param("telephone", "1242342323"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void updateOwnerPostNotValidTest() throws Exception{
        mockMvc.perform(post("/owners/{ownerId}/edit", 1)
                    .param("firstName", "Robin")
                    .param("lastName", "Hood")
                    .param("address", "Nottingham 2nd Street"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void newOwnerPostValidTest() throws Exception{
        mockMvc.perform(post("/owners/new")
                    .param("firstName", "Robin")
                    .param("lastName", "Hood")
                    .param("address", "Nottingham 2nd Street")
                    .param("city", "Nottingham")
                    .param("telephone", "1242342323"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void newOwnerPostNotValidTest() throws Exception{
        mockMvc.perform(post("/owners/new")
                    .param("firstName", "Robin")
                    .param("lastName", "Hood")
                    .param("city", "Nottingham"))
                .andExpect(status().isOk())
                    .andExpect(model().attributeHasErrors("owner"))
                    .andExpect(model().attributeHasFieldErrors("owner", "address"))
                    .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                    .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void initCreationFormTest() throws Exception{
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void returnListOfOwnersTest() throws Exception{
        given(clinicService.findOwnerByLastName("")).willReturn(Lists.newArrayList(new Owner(), new Owner()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));
//      Remember that captors fail without Extending MockitoExtension.class
        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("");
    }

    @Test
    void findOwnerOneResultTest() throws Exception{
        Owner soLonely = new Owner();
        final Integer id = 1;
        final String lastName = "FindJustOne";
        soLonely.setId(id);
        given(clinicService.findOwnerByLastName(lastName)).willReturn(Lists.newArrayList(soLonely));

        mockMvc.perform(get("/owners")
                .param("lastName", lastName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + id));
//      Remember that captors fail without Extending MockitoExtension.class
        then(clinicService).should().findOwnerByLastName(anyString());

    }

    @Test
    void findByNameNotFoundTest() throws Exception{
        mockMvc.perform(get("/owners").param("lastName", "Don't find me!"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }
}