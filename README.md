
## Instrucciones para ejecutar la aplicación en tu PC

### Requisitos previos

Asegúrate de tener instalados los siguientes programas:

- **Java 20** o superior
    - Si no tienes Java 20, puedes descargarlo desde: [Descargar JDK 20](https://jdk.java.net/20/)
    - Agregar la variable de entorno JAVA_HOME con la ruta a tu jdk:
    Ejemplo: `export JAVA_HOME=/home/usuario/.jdks/corretto-20.0.2.1/`
- **Maven** (opcional - solo si no usas el wrapper `./mvnw`)
    
    Si necesitas instalar Maven, sigue estas instrucciones:
    
    [Instalar Maven](https://maven.apache.org/install.html)
    
- **MySQL**
    
    Asegúrate de tener MySQL-8 instalado en tu PC:
    
    [Instalar MySQL](https://dev.mysql.com/downloads/installer/)
    
    Crear una base de datos llamada bonpland con `CREATE SCHEMA bonpland;`
    

### **Ejecutar el proyecto**

- Ir hasta la raiz del proyecto donde se encuentra el archivo pom.xml
- **OPCION 1:**
    - **Configurar variables de entorno en tu sistema:**
        - **DB_USER**: El nombre de usuario de MySQL (por ejemplo, `root`).
        - **DB_PASS**: La contraseña de MySQL.
        - **DB_URL**: La URL de la base de datos. Ejemplo: `jdbc:mysql://localhost:3306/bonpland`
    - **Ejecutar** `./mvnw spring-boot:run`
- **OPCION 2:**
    - Ejecutar en la terminal el siguiente comando:
    - `DB_USER=tu-usuario DB_PASS=tu-password DB_URL=jdbc:mysql://localhost:3306/bonpland ./mvnw spring-boot:run`
