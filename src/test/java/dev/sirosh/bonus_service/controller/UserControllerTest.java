package dev.sirosh.bonus_service.controller;

import dev.sirosh.bonus_service.AbstractIntegrationTest;
import dev.sirosh.bonus_service.entity.Role;
import dev.sirosh.bonus_service.entity.User;
import dev.sirosh.bonus_service.repository.UserRepository;
import dev.sirosh.bonus_service.service.BonusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractIntegrationTest {
    @MockBean
    BonusService bonusService;
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserRepository userRepository;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void register_normal() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test_user\", \"password\": \"123123\"}");

        mvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        Optional<User> savedUser = userRepository.findByUsername("test_user");
        User expectedUser = User.builder()
                .id(1L)
                .username("test_user")
                .password("123123")
                .role(Role.USER)
                .build();

        assertThat(savedUser).isPresent();
        assertThat(savedUser.get())
                .usingRecursiveComparison()
                .ignoringFields("password") //todo write comparator for password check
                .isEqualTo(expectedUser);

    }



    @Test
    void register_alreadyExists() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"admin\", \"password\": \"admin\"}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"message\":\"user already exists\"}", false));
    }

    @Test
    void register_badRequestBlank() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \" \", \"password\": \"\"}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"message\":\"username field should not be empty\"},{\"message\":\"password field should not be empty\"}]", false));
    }

    @Test
    void register_badRequestNoFields() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"message\":\"username field should not be empty\"},{\"message\":\"password field should not be empty\"}]", false));
    }


    @Test
    void login_admin() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"admin\", \"password\": \"admin\"}");

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs"));
    }


    @Test
    @Sql(scripts = "classpath:/test_user_seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void login_user() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test_user\", \"password\": \"123123\"}");

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsImlzcyI6Intqd3QuaXNzdWVyOidib251c19wcm9ncmFtJ30iLCJ1c2VybmFtZSI6InRlc3RfdXNlciJ9.X8h08WYBt-kYyUlu0w1D_B_qG-9mGmBh2zLX50RjXZY"));
    }

    @Test
    @Sql(scripts = "classpath:/test_user_seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void login_wrongPassword() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test_user\", \"password\": \"wrong_password\"}");

        mvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("{\"message\":\"wrong password !\"}", false));
    }


    @Test
    void login_wrongUsername() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"wrong_username\", \"password\": \"wrong_password\"}");

        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"user not found\"}", false));
    }

    @Test
    void login_badRequestBlank() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \" \", \"password\": \"\"}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"message\":\"username field should not be empty\"},{\"message\":\"password field should not be empty\"}]", false));
    }

    @Test
    void login_badRequestNoFields() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"message\":\"username field should not be empty\"},{\"message\":\"password field should not be empty\"}]", false));
    }

    @Test
    @Sql(scripts = "classpath:/test_user_seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getSelf_user() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/user/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsImlzcyI6Intqd3QuaXNzdWVyOidib251c19wcm9ncmFtJ30iLCJ1c2VybmFtZSI6InRlc3RfdXNlciJ9.X8h08WYBt-kYyUlu0w1D_B_qG-9mGmBh2zLX50RjXZY");

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"username\":\"test_user\",\"role\": \"USER\", \"bonus\": {\"count\": 0}}", false));
    }

    @Test
    void getSelf_admin() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/user/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs");

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"username\":\"admin\",\"role\": \"ADMIN\", \"bonus\": {\"count\": 0}}", false));
    }

    @Test
    @Sql(scripts = "classpath:/test_user_seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getSelf_anonymous() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/user/me")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isForbidden());
    }


    @Test
    @Sql(scripts = "classpath:/test_user_seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getList_user() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsImlzcyI6Intqd3QuaXNzdWVyOidib251c19wcm9ncmFtJ30iLCJ1c2VybmFtZSI6InRlc3RfdXNlciJ9.X8h08WYBt-kYyUlu0w1D_B_qG-9mGmBh2zLX50RjXZY");

        mvc.perform(requestBuilder)
                .andExpect(status().isForbidden());
    }

    @Test
    void getList_admin() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpc3MiOiJ7and0Lmlzc3VlcjonYm9udXNfcHJvZ3JhbSd9IiwidXNlcm5hbWUiOiJhZG1pbiJ9.ch4C91AjvqyEjJJe3yAjTlP-7d4maBqzbdUUDEFPqEs");

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"username\":\"admin\",\"role\": \"ADMIN\", \"bonus\": {\"count\": 0}}]", false));
    }

    @Test
    @Sql(scripts = "classpath:/test_user_seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getList_anonymous() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/user")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isForbidden());
    }
}