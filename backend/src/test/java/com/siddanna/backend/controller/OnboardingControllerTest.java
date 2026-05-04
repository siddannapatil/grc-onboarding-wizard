package com.siddanna.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddanna.backend.model.Onboarding;
import com.siddanna.backend.repository.OnboardingRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class OnboardingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OnboardingRepository repo;

    
    @Test
    void testCreate() throws Exception {
        Onboarding obj = new Onboarding();
        obj.setName("Test");
        obj.setEmail("test" + System.currentTimeMillis() + "@gmail.com");
        obj.setRole("Dev");
        obj.setDescription("Testing");

        mockMvc.perform(post("/onboarding")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(obj)))
                .andExpect(status().isOk());
    }

    
    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/onboarding"))
                .andExpect(status().isOk());
    }

    
    @Test
    void testUpdate() throws Exception {
        Onboarding obj = new Onboarding();
        obj.setName("Old");
        obj.setEmail("old" + System.currentTimeMillis() + "@gmail.com");
        obj.setRole("Dev");
        obj.setDescription("Old");

        obj = repo.save(obj);
        obj.setName("Updated");

        mockMvc.perform(put("/onboarding/" + obj.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(obj)))
                .andExpect(status().isOk());
    }

    
    @Test
    void testDelete() throws Exception {
        Onboarding obj = new Onboarding();
        obj.setName("Delete");
        obj.setEmail("delete" + System.currentTimeMillis() + "@gmail.com");
        obj.setRole("Dev");
        obj.setDescription("Delete");

        obj = repo.save(obj);

        mockMvc.perform(delete("/onboarding/" + obj.getId()))
                .andExpect(status().isOk());
    }

    
    @Test
    void testGetById_NotFound() throws Exception {
        mockMvc.perform(get("/onboarding/999999"))
                .andExpect(status().isNotFound());
    }

    
    @Test
    void testUpdate_NotFound() throws Exception {
        Onboarding obj = new Onboarding();
        obj.setName("X");
        obj.setEmail("x" + System.currentTimeMillis() + "@gmail.com");
        obj.setRole("Dev");

        mockMvc.perform(put("/onboarding/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(obj)))
                .andExpect(status().isNotFound());
    }

    
    @Test
    void testDelete_NotFound() throws Exception {
        mockMvc.perform(delete("/onboarding/999999"))
                .andExpect(status().isNotFound());
    }

    
    @Test
    void testCreate_InvalidInput() throws Exception {
        mockMvc.perform(post("/onboarding")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    
    @Test
    void testWrongMethod() throws Exception {
        mockMvc.perform(put("/onboarding"))
                .andExpect(status().isMethodNotAllowed());
    }
}