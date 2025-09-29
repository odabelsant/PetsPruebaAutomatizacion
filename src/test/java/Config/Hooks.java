package Config;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks extends Config {

    @Before
    public void setUp() {
        System.out.println(">>> Iniciando driver...");
        startDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && driver != null) {
                System.out.println(">>> Escenario falló, capturando screenshot...");
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "error.png");
            }
        } catch (Exception e) {
            System.out.println(">>> Error al tomar screenshot: " + e.getMessage());
        } finally {
            if (driver != null) {
                System.out.println(">>> Cerrando driver...");
                quitDriver();
            }
        }
    }
}

