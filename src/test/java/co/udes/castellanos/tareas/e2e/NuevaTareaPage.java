package co.udes.castellanos.tareas.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object que encapsula la página del formulario de nueva tarea (/tareas/nueva).
 * Todos los selectores se declaran como constantes privadas (By.id / By.cssSelector).
 */
public class NuevaTareaPage {

    // ── Selectores encapsulados ───────────────────────────────────────────────
    private static final By INPUT_TITULO      = By.id("input-titulo");
    private static final By INPUT_DESCRIPCION = By.id("input-descripcion");
    private static final By BTN_GUARDAR       = By.id("btn-guardar");
    private static final By FORM_TAREA        = By.id("form-nueva-tarea");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public NuevaTareaPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ── Acciones ──────────────────────────────────────────────────────────────

    /**
     * Ingresa el título en el campo de texto.
     *
     * @param titulo texto a escribir
     * @return esta página (fluent API)
     */
    public NuevaTareaPage escribirTitulo(String titulo) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(INPUT_TITULO));
        driver.findElement(INPUT_TITULO).sendKeys(titulo);
        return this;
    }

    /**
     * Ingresa la descripción en el área de texto.
     *
     * @param descripcion texto a escribir
     * @return esta página (fluent API)
     */
    public NuevaTareaPage escribirDescripcion(String descripcion) {
        driver.findElement(INPUT_DESCRIPCION).sendKeys(descripcion);
        return this;
    }

    /**
     * Hace clic en "Guardar Tarea" y regresa al Page Object de la lista.
     *
     * @return TareasPage después de la redirección
     */
    public TareasPage guardar() {
        wait.until(ExpectedConditions.elementToBeClickable(BTN_GUARDAR)).click();
        return new TareasPage(driver);
    }

    /**
     * Verifica si el formulario está presente y visible en el DOM.
     */
    public boolean formularioVisible() {
        return driver.findElement(FORM_TAREA).isDisplayed();
    }

    /**
     * Retorna el título de la página del navegador.
     */
    public String obtenerTituloPagina() {
        return driver.getTitle();
    }
}
