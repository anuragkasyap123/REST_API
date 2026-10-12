package com.example.restapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsGreetingForProvidedName() throws Exception {
        mockMvc.perform(get("/api/greetings").param("name", "Anura"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Anura!"));
    }

    @Test
    void defaultsNameToWorld() throws Exception {
        mockMvc.perform(get("/api/greetings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, world!"));
    }

    @Test
    void readsNameFromPath() throws Exception {
        mockMvc.perform(get("/api/greetings/Anura"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Anura!"));
    }
}