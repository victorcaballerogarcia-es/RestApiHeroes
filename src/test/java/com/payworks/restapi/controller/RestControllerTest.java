package com.payworks.restapi.controller;

import com.payworks.restapi.IntegrationConfigurationTest;
import com.payworks.restapi.config.WebAppConfig;
import com.payworks.restapi.repository.HeroRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class)
public class RestControllerTest extends IntegrationConfigurationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    HeroRepository heroRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void testGetHeroes() throws Exception {
        mockMvc.perform(get("/heroes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].pseudonym", is("Robin")))
                .andExpect(jsonPath("$[1].pseudonym", is("Batman")));
    }

    @Test
    public void testGetHeroById() throws Exception {
        mockMvc.perform(get("/heroes/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.pseudonym", is("Batman")));
    }

    @Test
    public void testGetHeroByIdEmpty() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/heroes/{id}", 10))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content, isEmptyString());
    }

    @Test
    public void testGetHeroByPseudonym() throws Exception {
        mockMvc.perform(get("/heroes/pseudonym/{pseudonym}", "Robin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.pseudonym", is("Robin")));
    }

    @Test
    public void testCreateHero() throws Exception {
        String json = "{\"name\": \"Clark Kent\",\"pseudonym\": \"Superman\",\"publisher\": \"DC\",\"skills\": [\"Solar energy absorption and healing factor\", \"Solar flare and heat vision\",\"Superlongevity\", \"Solar invulnerability\", \"Flight\"],\"allies\": [\"Supergirl\"],\"firstAppearance\": \"1938-06-01\"}";

        MvcResult mvcResult = mockMvc.perform(post("/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content, equalTo(RestController.MESSAGE_HERO_CREATED));
    }

}
