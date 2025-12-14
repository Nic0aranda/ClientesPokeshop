package com.example.ecommerce_usuarios.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.data-seeding")
public class DataSeedingProperties {
    private boolean enabled = true;
    private String roles = "VENDEDOR,CLIENTE";
    private String vendedorEmail = "vendedor@ecommerce.com";
    private String vendedorPassword = "Vendedor123";

    // Getters y setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
    
    public String getVendedorEmail() { return vendedorEmail; }
    public void setVendedorEmail(String vendedorEmail) { this.vendedorEmail = vendedorEmail; }
    
    public String getVendedorPassword() { return vendedorPassword; }
    public void setVendedorPassword(String vendedorPassword) { this.vendedorPassword = vendedorPassword; }
}