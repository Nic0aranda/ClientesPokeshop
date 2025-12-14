package com.example.ecommerce_usuarios.util;

import org.springframework.stereotype.Component;

@Component
public class SimplePasswordEncoder {
    
    public String encode(String password) {
        // Método simple de encoding para testing
        return "encoded_" + password;
    }
}
