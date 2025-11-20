package com.example.ecommerce_usuarios.controller;

import com.example.ecommerce_usuarios.model.rolEntity;
import com.example.ecommerce_usuarios.service.RolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllRoles_shouldReturnOk() throws Exception {
        rolEntity mockRol = Mockito.mock(rolEntity.class);
        Mockito.when(rolService.findAll()).thenReturn(List.of(mockRol));

        mockMvc.perform(get("/api/v1/roles"))
               .andExpect(status().isOk());
    }

    @Test
    void getRoleById_found_shouldReturnOk() throws Exception {
        rolEntity mockRol = Mockito.mock(rolEntity.class);
        Mockito.when(rolService.findById(1L)).thenReturn(Optional.of(mockRol));

        mockMvc.perform(get("/api/v1/roles/1"))
               .andExpect(status().isOk());
    }

    @Test
    void getRoleById_notFound_shouldReturn404() throws Exception {
        Mockito.when(rolService.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/roles/2"))
               .andExpect(status().isNotFound());
    }

    @Test
    void createRole_valid_shouldReturnCreated() throws Exception {
        Map<String,Object> body = Map.of("name", "Admin");
        rolEntity returned = Mockito.mock(rolEntity.class);
        Mockito.when(rolService.save(any(rolEntity.class))).thenReturn(returned);

        mockMvc.perform(post("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
               .andExpect(status().isCreated());
    }

    @Test
    void updateRole_existing_shouldReturnOk() throws Exception {
        Map<String,Object> body = Map.of("name","Updated");
        rolEntity returned = Mockito.mock(rolEntity.class);
        Mockito.when(rolService.update(eq(1L), any(rolEntity.class))).thenReturn(returned);

        mockMvc.perform(put("/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
               .andExpect(status().isOk());
    }

    @Test
    void updateRole_notFound_shouldReturn404() throws Exception {
        Map<String,Object> body = Map.of("name","Nope");
        Mockito.when(rolService.update(eq(2L), any(rolEntity.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/v1/roles/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
               .andExpect(status().isNotFound());
    }

    @Test
    void deleteRole_existing_shouldReturnNoContent() throws Exception {
        doNothing().when(rolService).delete(1L);

        mockMvc.perform(delete("/api/v1/roles/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    void deleteRole_notFound_shouldReturn404() throws Exception {
        doThrow(new RuntimeException()).when(rolService).delete(2L);

        mockMvc.perform(delete("/api/v1/roles/2"))
               .andExpect(status().isNotFound());
    }
}
