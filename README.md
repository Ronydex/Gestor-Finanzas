#  Gestor de Finanzas Personales (Método Kakeibo)

¡Bienvenido a **Gestor-Finanzas**! Esta es una aplicación web robusta e independiente diseñada para la gestión del flujo de caja mensual. El sistema adopta la filosofía japonesa **Kakeibo**, ayudando a los usuarios no solo a registrar números, sino a concientizar sus hábitos de consumo mediante metas de ahorro y la clasificación inteligente de sus gastos.

Desarrollado con una arquitectura limpia en el backend usando **Java** y **Spring Boot**, y una interfaz dinámica y responsiva en el frontend.

---


##  Características Clave

*   **Filosofía Kakeibo Integrada:** Clasificación obligatoria de egresos en 4 pilares esenciales: *Supervivencia* (necesidades), *Ocio* (deseos), *Cultura* e *Extras* (imprevistos).
*   **Planificación de Metas:** Módulo interactivo para fijar y monitorear objetivos de ahorro mensuales.
*   **Visualización de Datos:** Gráfica de dona interactiva renderizada con **Chart.js** que muestra la distribución en tiempo real de los ingresos y egresos.
*   **Interfaz Adaptativa (Modo Oscuro/Claro):** Alternador de tema nativo (Dark/Light mode) persistente a través de `localStorage` para evitar parpadeos visuales.
*   **Experiencia de Usuario Fluida (UX):** Flujo asíncrono para confirmaciones de eliminación crítica utilizando **SweetAlert2**.

---

##  Stack Tecnológico

### Backend
*   **Lenguaje:** Java 17+
*   **Framework Principal:** Spring Boot (MVC)
*   **Capa de Persistencia:** Spring Data JPA
*   **Motor de Plantillas:** Thymeleaf

### Frontend
*   **Estilos:** Bootstrap 5 (con Bootstrap Icons)
*   **Gráficas:** Chart.js (CDN)
*   **Alertas Dinámicas:** SweetAlert2

---
##  Requisitos Previos

Antes de ejecutar la aplicación localmente, asegúrate de tener instalado:
*   **Java JDK 17** o superior.
*   **Maven** (para la gestión de dependencias).
*   Tu motor de bases de datos preferido configurado en el entorno (MySQL / PostgreSQL / H2).

---


##  Instalación y Ejecución Local

1. **Clonar el repositorio:**

   git clone [https://github.com/Ronydex/Gestor-Finanzas.git](https://github.com/Ronydex/Gestor-Finanzas.git)

   cd Gestor-Finanzas
2. Configurar las propiedades del entorno:

   Modifica el archivo src/main/resources/application.properties con tus credenciales de base de datos locales:

   spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos

   spring.datasource.username=tu_usuario

   spring.datasource.password=tu_contraseña

   spring.jpa.hibernate.ddl-auto=update

3. Compilar y ejecutar la aplicación:

    mvn spring-boot:run

4. Acceder en el navegador:

   Abre http://localhost:8080/ver-todo (o la ruta de inicio configurada).

## Vista Previa del Sistema

### Control de Temas (Light / Dark Mode)
Demostración de la persistencia del tema visual adaptativo a través de localStorage para mejorar la experiencia del usuario.

| Interfaz en Modo Claro | Interfaz en Modo Oscuro |
| :---: | :---: |
| <img src="img/EVIDENCIA%201.png" alt="Interfaz en Modo Claro" width="450" /> | <img src="img/EVIDENCIA%202.png" alt="Interfaz en Modo Oscuro" width="450" /> |

---

### Reactividad de Datos y Flujos de Trabajo (UX)
Muestra del recálculo dinámico de fondos en la gráfica/saldos y la confirmación asíncrona segura antes de realizar operaciones destructivas en la base de datos.

| Actualización de Flujo y Saldos | Confirmación Crítica (SweetAlert2) |
| :---: | :---: |
| <img src="img/EVIDENCIA%203png.png" alt="Actualización de Flujo y Saldos" width="450" /> | <img src="img/EVIDENCIA%20BORRADO.png" alt="Confirmación Crítica con SweetAlert2" width="450" /> |
