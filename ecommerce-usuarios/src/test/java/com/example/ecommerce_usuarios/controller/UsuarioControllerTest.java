package com.example.ecommerce_usuarios.controller;

import com.example.ecommerce_usuarios.model.userEntity;
import com.example.ecommerce_usuarios.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers_shouldReturnOk() throws Exception {
        userEntity mockUser = Mockito.mock(userEntity.class);
        Mockito.when(usuarioService.findAll()).thenReturn(List.of(mockUser));

        mockMvc.perform(get("/api/v1/usuarios"))
               .andExpect(status().isOk());
    }

    @Test
    void getUserById_found_shouldReturnOk() throws Exception {
        userEntity mockUser = Mockito.mock(userEntity.class);
        Mockito.when(usuarioService.findById(1L)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/api/v1/usuarios/1"))
               .andExpect(status().isOk());
    }

    @Test
    void getUserById_notFound_shouldReturn404() throws Exception {
        Mockito.when(usuarioService.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/usuarios/2"))
               .andExpect(status().isNotFound());
    }

    @Test
    void createUser_shouldReturnCreated() throws Exception {
        Map<String,Object> body = Map.of("email","test@example.com","name","Test");
        userEntity returned = Mockito.mock(userEntity.class);
        Mockito.when(usuarioService.save(any(userEntity.class))).thenReturn(returned);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
               .andExpect(status().isCreated());
    }

    @Test
    void updateUser_existing_shouldReturnOk() throws Exception {
        Map<String,Object> body = Map.of("name","Updated");
        userEntity returned = Mockito.mock(userEntity.class);
        Mockito.when(usuarioService.update(eq(1L), any(userEntity.class))).thenReturn(returned);

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
               .andExpect(status().isOk());
    }

    @Test
    void updateUser_notFound_shouldReturn404() throws Exception {
        Map<String,Object> body = Map.of("name","Nope");
        Mockito.when(usuarioService.update(eq(2L), any(userEntity.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/v1/usuarios/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
               .andExpect(status().isNotFound());
    }

    @Test
    void updateUserStatus_success_shouldReturnNoContent() throws Exception {
        doNothing().when(usuarioService).updateStatus(1L, true);

        mockMvc.perform(put("/api/v1/usuarios/1/status")
                .param("status", "true"))
               .andExpect(status().isNoContent());
    }

    @Test
    void updateUserStatus_notFound_shouldReturn404() throws Exception {
        doThrow(new RuntimeException()).when(usuarioService).updateStatus(2L, true);

        mockMvc.perform(put("/api/v1/usuarios/2/status")
                .param("status", "true"))
               .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_existing_shouldReturnNoContent() throws Exception {
        doNothing().when(usuarioService).delete(1L);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_notFound_shouldReturn404() throws Exception {
        doThrow(new RuntimeException()).when(usuarioService).delete(2L);

        mockMvc.perform(delete("/api/v1/usuarios/2"))
               .andExpect(status().isNotFound());
    }
}
