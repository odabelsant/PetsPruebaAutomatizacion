package Config;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LocatorUtils;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase central de utilidades Selenium.
 * - Contiene driver / wait.
 * - Contiene helpers genéricos (sin localizadores embebidos).
 */
public class Config {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    // ---------- Inicialización y limpieza ----------
    public synchronized void startDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            // options.addArguments("--disable-gpu");
            // options.addArguments("--no-sandbox");
            // options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
    }

    public synchronized void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Warning: error al cerrar driver: " + e.getMessage());
            } finally {
                driver = null;
                wait = null;
            }
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    protected static WebDriverWait getWait() {
        return wait;
    }

    // ---------- Helpers básicos ----------
    public void writeText(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    public void selectDate(By datePickerLocator, String date) {
        WebElement datePicker = waitForElement(datePickerLocator);
        datePicker.click();
        // implementación específica depende del datepicker
    }

    public void selectDropdownOption(By dropdownLocator, String optionText) {
        WebElement dropdown = waitForElement(dropdownLocator);
        dropdown.click();
        By optionLocator = By.xpath(".//option[text()='" + optionText + "']");
        WebElement option = waitForElement(optionLocator);
        option.click();
    }

    public WebElement waitForElement(By locator) {
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait no inicializado. Llama a startDriver() primero.");
        }
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementTime(By locator) {
        if (driver == null) {
            throw new IllegalStateException("Driver no inicializado. Llama a startDriver() primero.");
        }
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickableElement(By locator) {
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait no inicializado. Llama a startDriver() primero.");
        }
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForClickableElement(By locator, int seconds) {
        if (driver == null) {
            throw new IllegalStateException("Driver no inicializado. Llama a startDriver() primero.");
        }
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return customWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Click robusto (alias de clickElement, útil por conveniencia)
    public void clickElement(By locator) {
        // 1) intento normal
        try {
            waitForClickableElement(locator).click();
            return;
        } catch (Exception e) {
            System.out.println("DEBUG: clickElement intento normal falló: " + e.getMessage());
        }

        // 2) intento extendido
        try {
            waitForClickableElement(locator, 20).click();
            return;
        } catch (Exception e) {
            System.out.println("DEBUG: clickElement intento 20s falló: " + e.getMessage());
        }

        // 3) js click
        try {
            jsClick(locator);
            return;
        } catch (Exception e) {
            System.out.println("DEBUG: clickElement jsClick falló: " + e.getMessage());
        }

        // 4) guardar evidencia y lanzar excepción
        try {
            saveDebugPageSource("clickElement_failed_" + sanitizeLocator(locator));
            saveDebugScreenshot("clickElement_failed_" + sanitizeLocator(locator));
        } catch (Exception ex) {
            System.err.println("DEBUG: no se pudo guardar evidencia: " + ex.getMessage());
        }

        throw new RuntimeException("No se pudo clicar el elemento: " + locator.toString());
    }

    private String sanitizeLocator(By locator) {
        String s = locator.toString().replaceAll("[^a-zA-Z0-9_-]", "_");
        if (s.length() > 120) s = s.substring(0, 120);
        return s;
    }

    public void pressEnterElement(By locator) {
        WebElement element = waitForElement(locator);
        element.sendKeys(Keys.ENTER);
    }

    public void doubleClickElement(By locator) {
        WebElement element = waitForClickableElement(locator);
        new Actions(driver).doubleClick(element).perform();
    }

    public String getElementText(By locator) {
        return waitForElement(locator).getText();
    }

    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    public String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }

    public void enterText(By locator, String text) {
        writeText(locator, text);
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void jsClick(By locator) {
        WebElement element = waitForElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void hoverOverElement(By locator) {
        WebElement element = waitForElement(locator);
        new Actions(driver).moveToElement(element).perform();
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = waitForElement(sourceLocator);
        WebElement target = waitForElement(targetLocator);
        new Actions(driver).dragAndDrop(source, target).perform();
    }

    public void pressKey(By locator, Keys key) {
        WebElement element = waitForElement(locator);
        element.sendKeys(key);
    }

    public void switchToFrame(By locator) {
        WebElement frame = waitForElement(locator);
        driver.switchTo().frame(frame);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToNewWindow() {
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
    }

    public void switchToNewTab() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public void closeCurrentWindowAndSwitchToOriginal() {
        String originalHandle = driver.getWindowHandle();
        driver.close();
        driver.switchTo().window(originalHandle);
    }

    public void closeCurrentWindowAndSwitchBack() {
        driver.close();
        switchToDefaultContent();
    }

    public void accederAPagina(String url) {
        ensureDriverStarted();
        driver.get(url);
    }

    public void accessPageMain(String url) {
        ensureDriverStarted();
        driver.get(url);
    }

    public String obtenerTitulo(By locator) {
        WebElement element = waitForElementTime(locator);
        return element.getText();
    }

    protected String getCurrentUrlResult() {
        return (driver != null) ? driver.getCurrentUrl() : "";
    }

    // ---------- Debug ----------
    public void saveDebugScreenshot(String name) {
        try {
            if (driver == null) return;
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Path out = Paths.get("build", "debug");
            Files.createDirectories(out);
            Path file = out.resolve(name + ".png");
            Files.write(file, screenshot);
            System.out.println("DEBUG: Screenshot guardado en: " + file.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("DEBUG: No se pudo guardar screenshot: " + e.getMessage());
        }
    }

    public void saveDebugPageSource(String name) {
        try {
            if (driver == null) return;
            String html = driver.getPageSource();
            Path out = Paths.get("build", "debug");
            Files.createDirectories(out);
            Path file = out.resolve(name + ".html");
            Files.writeString(file, html);
            System.out.println("DEBUG: Page source guardado en: " + file.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("DEBUG: No se pudo guardar page source: " + e.getMessage());
        }
    }

    private void ensureDriverStarted() {
        if (driver == null) {
            throw new IllegalStateException("Driver no iniciado. Llama a startDriver() antes de esta operación.");
        }
    }

    public void retryAction(Runnable action, int attempts) {
        if (attempts < 1) attempts = 1;
        int tries = 0;
        while (true) {
            tries++;
            try {
                action.run();
                return;
            } catch (Exception e) {
                System.err.println("WARN: intento " + tries + " falló: " + e.getMessage());
                if (tries >= attempts) {
                    throw e;
                }
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
        }
    }

    // ---------- UTILIDADES GENÉRICAS (AHORA SIN LOCALIZADORES) ----------
    /**
     * clickMenuForPet ahora usa LocatorUtils para obtener los By,
     * por lo que Config no contiene la definición de los localizadores.
     */
    protected void clickMenuForPet(String petType, org.openqa.selenium.By... possibleLocators) {
        try {
            if (possibleLocators != null) {
                for (org.openqa.selenium.By loc : possibleLocators) {
                    if (loc == null) continue;
                    if (isElementPresent(loc)) {
                        clickElement(loc);
                        return;
                    }
                }
            }

            // fallbacks usando LocatorUtils (no hay localizadores dentro de Config)
            org.openqa.selenium.By linkTextLocator = LocatorUtils.menuLink(petType);
            if (isElementPresent(linkTextLocator)) {
                clickElement(linkTextLocator);
                return;
            }

            org.openqa.selenium.By xpathExact = LocatorUtils.xpathExactForLink(petType);
            if (isElementPresent(xpathExact)) {
                clickElement(xpathExact);
                return;
            }

            org.openqa.selenium.By xpathContains = LocatorUtils.xpathContainsForLink(petType);
            if (isElementPresent(xpathContains)) {
                jsClick(xpathContains);
                return;
            }

            saveDebugPageSource("menu_not_found_" + petType.replaceAll("\\s+","_"));
            saveDebugScreenshot("menu_not_found_" + petType.replaceAll("\\s+","_"));
            throw new RuntimeException("No se encontró el menú ni se pudo clicar para petType=" + petType);

        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            saveDebugPageSource("menu_click_error_" + petType.replaceAll("\\s+","_"));
            saveDebugScreenshot("menu_click_error_" + petType.replaceAll("\\s+","_"));
            throw new RuntimeException("Error al intentar clicar el menú: " + petType + " -> " + e.getMessage(), e);
        }
    }

    protected void validateMenuGeneric(String petType, org.openqa.selenium.By... possibleLocators) {
        if (possibleLocators != null && possibleLocators.length > 0) {
            boolean found = false;
            for (org.openqa.selenium.By loc : possibleLocators) {
                if (loc != null && isElementPresent(loc)) { found = true; break; }
            }
            if (found) return;
        }

        // fallback a linkText vía LocatorUtils
        org.openqa.selenium.By locator = LocatorUtils.menuLink(petType);
        if (isElementPresent(locator)) return;

        saveDebugPageSource("menu_validate_failed_" + petType.replaceAll("\\s+","_"));
        saveDebugScreenshot("menu_validate_failed_" + petType.replaceAll("\\s+","_"));
        throw new AssertionError("No existe el menú para: " + petType);
    }

    protected void proceedToCheckoutGeneric(By proceedBtn, By signInLink) {
        try {
            waitForClickableElement(proceedBtn, 20);
            clickElement(proceedBtn);

            waitForElementTime(signInLink); // espera extendida
            if (!isElementPresent(signInLink)) {
                saveDebugPageSource("proceedToCheckout_no_signin");
                saveDebugScreenshot("proceedToCheckout_no_signin");
                throw new RuntimeException("No se visualizó Sign In luego de proceedToCheckout");
            }
        } catch (Exception e) {
            saveDebugPageSource("proceedToCheckout_error");
            saveDebugScreenshot("proceedToCheckout_error");
            throw new RuntimeException("Error en proceedToCheckout: " + e.getMessage(), e);
        }
    }

    protected void verifyCategoryPageGeneric(String petType) {
        String url = getCurrentUrlResult();
        if (url != null && url.toUpperCase().contains("CATEGORYID=" + petType.toUpperCase())) {
            return;
        }

        // ahora el locator del catálogo viene de LocatorUtils
        By catalogTable = LocatorUtils.catalogTable();
        if (isElementPresent(catalogTable)) {
            return;
        }

        saveDebugPageSource("verifyCategory_failed_" + petType.replaceAll("\\s+","_"));
        saveDebugScreenshot("verifyCategory_failed_" + petType.replaceAll("\\s+","_"));
        throw new AssertionError("La página actual no parece ser la categoría esperada: " + petType + " (url=" + url + ")");
    }

    // Helper reutilizable para validar que la instancia nav fue inicializada desde PageBoard
    protected void ensureNavInitialized(Object nav, String messageIfNull) {
        if (nav == null) {
            throw new IllegalStateException(messageIfNull);
        }
    }
}
