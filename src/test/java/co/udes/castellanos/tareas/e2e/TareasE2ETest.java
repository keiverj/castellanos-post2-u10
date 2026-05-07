package co.udes.castellanos.tareas.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Suite de pruebas E2E con Selenium WebDriver usando el patrón Page Object Model.
 * La aplicación arranca en modo headless en el puerto 8080.
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class TareasE2ETest {

    private static final String BASE_URL = "http://localhost:8080";

    private WebDriver driver;
    private TareasPage tareasPage;

    @BeforeEach
    void setUp() {
        // WebDriverManager descarga automáticamente el chromedriver compatible
        WebDriverManager.chromedriver().setup();

        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless");          // Sin ventana
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        opts.addArguments("--disable-gpu");
        opts.addArguments("--window-size=1280,800");

        driver = new ChromeDriver(opts);
        driver.get(BASE_URL + "/tareas");
        tareasPage = new TareasPage(driver).esperarCarga();
    }

    // ── Test 1 ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("La página /tareas carga con el título correcto")
    void paginaTareas_cargaCorrectamente() {
        assertThat(tareasPage.obtenerTituloPagina())
                .as("El título de la página debe contener 'Tareas'")
                .contains("Tareas");
    }

    // ── Test 2 ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("El botón 'Nueva Tarea' está visible en la página")
    void botonNuevaTarea_estaVisible() {
        assertThat(tareasPage.botonNuevaVisible())
                .as("El botón #btn-nueva debe estar visible")
                .isTrue();
    }

    // ── Test 3 ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Navegar a Nueva Tarea muestra el formulario")
    void navegarANuevaTarea_muestraFormulario() {
        NuevaTareaPage nuevaPage = tareasPage.irANuevaTarea();

        assertThat(nuevaPage.obtenerTituloPagina())
                .as("La página de nueva tarea debe contener 'Nueva Tarea'")
                .contains("Nueva Tarea");

        assertThat(nuevaPage.formularioVisible())
                .as("El formulario #form-nueva-tarea debe estar visible")
                .isTrue();
    }

    // ── Test 4 ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Crear una tarea desde el formulario la muestra en la lista")
    void crearTarea_apareceEnLaLista() {
        int tareasIniciales = tareasPage.contarTareas();

        TareasPage listaActualizada = tareasPage
                .irANuevaTarea()
                .escribirTitulo("Tarea E2E de prueba")
                .escribirDescripcion("Descripción de la tarea E2E")
                .guardar()
                .esperarCarga();

        assertThat(listaActualizada.contarTareas())
                .as("La lista debe tener una tarea más tras crear una nueva")
                .isGreaterThan(tareasIniciales);

        assertThat(listaActualizada.obtenerTitulo(0))
                .as("El título de la primera tarea debe coincidir con el creado")
                .contains("Tarea E2E de prueba");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
