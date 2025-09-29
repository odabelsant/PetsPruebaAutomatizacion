package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Clase principal para ejecutar los tests de Cucumber.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources", // ruta a tus .feature (puedes cambiar a PetManagement si prefieres)
        glue = {"Steps", "Config"},      // ajustado a los packages que mostraste (respeta mayúsculas)
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber-reports.json"
        },
        monochrome = true
)
public class TestRunners { }
