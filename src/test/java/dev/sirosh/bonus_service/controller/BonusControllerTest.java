package dev.sirosh.bonus_service.controller;

import dev.sirosh.bonus_service.AbstractIntegrationTest;
import dev.sirosh.bonus_service.service.BonusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BonusControllerTest extends AbstractIntegrationTest {
    @MockBean
    BonusService bonusService;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void bonusAdd_normal() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/bonus/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs")
                .content("{\"amount\": 10}");

        mvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }


    @Test
    void bonusAdd_negativeAmount() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/bonus/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs")
                .content("{\"amount\": -10}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{\"message\":\"amount should be positive value\"}]", false));
    }


    @Test
    void bonusAdd_noAmount() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/bonus/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs")
                .content("{}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{\"message\":\"amount value should be present\"}]", false));;
    }

    @Test
    void bonusSubtract_normal() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/bonus/subtract")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs")
                .content("{\"amount\": 10}");

        mvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }


    @Test
    void bonusSubtract_negativeAmount() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/bonus/subtract")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs")
                .content("{\"amount\": -10}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{\"message\":\"amount should be positive value\"}]", false));
    }


    @Test
    void bonusSubtract_noAmount() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/bonus/subtract")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs")
                .content("{}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{\"message\":\"amount value should be present\"}]", false));
        ;
    }
}