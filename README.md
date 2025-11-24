Iniciar el xampp 
Inicializar el apache 
Inicializar Mysql en el puerto "3306" 
Tener un usuario llamado "root" 
El usuario al menos en la configuracion por defecto no tiene contraseña (Este usuario deberia existir por defecto en nuestra bd)

Redirigirse con el cmd a la ubicacion de la carpeta.

Ejecutar este comando con el cmd: .\mvnw.cmd -DskipTests=true spring-boot:run

Para visualizar swagger ir a http://localhost:8080/swagger-ui/index.html

Con esto el microservicio deberia de ejecutarse

comando para realizar las pruebas: .\mvnw.cmd test

Usuario vendedor para pruebas
INSERT INTO users (names, email, password, status, rol_id) 
VALUES (
    'Vendedor Principal',       
    'vendedor@pokeshop.com',    
    'PokeVentas2025!',          
    1,                          
    1                          
);
