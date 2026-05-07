package co.udes.castellanos.tareas.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object que encapsula la página de lista de tareas (/tareas).
 * Todos los selectores se declaran como constantes privadas (By.id / By.cssSelector).
 */
public class TareasPage {

    // ── Selectores encapsulados ───────────────────────────────────────────────
    private static final By BTN_NUEVA    = By.id("btn-nueva");
    private static final By LISTA_TAREAS = By.id("lista-tareas");
    private static final By ITEMS_TAREA  = By.cssSelector(".tarea-item");
    private static final By TITULOS      = By.cssSelector(".tarea-item .titulo");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public TareasPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ── Acciones ──────────────────────────────────────────────────────────────

    /**
     * Cuenta el número de tareas visibles en la lista.
     *
     * @return número de elementos .tarea-item en el DOM
     */
    public int contarTareas() {
        return driver.findElements(ITEMS_TAREA).size();
    }

    /**
     * Navega a la página de nueva tarea haciendo clic en el botón principal.
     *
     * @return el Page Object de NuevaTareaPage
     */
    public NuevaTareaPage irANuevaTarea() {
        wait.until(ExpectedConditions.elementToBeClickable(BTN_NUEVA)).click();
        return new NuevaTareaPage(driver);
    }

    /**
     * Obtiene el título de la tarea en la posición indicada (0-indexed).
     *
     * @param indice posición en la lista
     * @return texto del título
     */
    public String obtenerTitulo(int indice) {
        List<WebElement> titulos = driver.findElements(TITULOS);
        return titulos.get(indice).getText();
    }

    /**
     * Espera a que la lista de tareas sea visible.
     */
    public TareasPage esperarCarga() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LISTA_TAREAS));
        return this;
    }

    /**
     * Retorna el título de la página del navegador.
     */
    public String obtenerTituloPagina() {
        return driver.getTitle();
    }

    /**
     * Verifica si el botón "Nueva Tarea" está visible y habilitado.
     */
    public boolean botonNuevaVisible() {
        return driver.findElement(BTN_NUEVA).isDisplayed();
    }
}
