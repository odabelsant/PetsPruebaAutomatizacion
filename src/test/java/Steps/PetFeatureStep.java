package Steps;

import Pages.PageBoard;
import io.cucumber.java.en.*;
import org.junit.Assert;

/**
 * Step definitions para Pet.feature
 * - Usa PageBoard como fachada para las acciones en la UI.
 * - Incluye reintentos simples para pasos propensos a ser flaky.
 */
public class PetFeatureStep {

    private final PageBoard pb = new PageBoard();

    @Given("Accedes a la pagina {string}")
    public void accedes_a_la_pagina(String url) {
        System.out.println(">>> Accediendo a la página: " + url);
        try {
            pb.accessPageURL(url);
        } catch (Exception e) {
            System.err.println("ERROR: No se pudo acceder a la URL: " + e.getMessage());
            throw e;
        }
    }

    @When("Le das clic en el menú superior {string}")
    public void le_das_clic_en_el_menu_superior(String petType) {
        System.out.println(">>> Entrando al menú: " + petType);
        try {
            pb.petSelect(petType);
        } catch (Exception e) {
            System.err.println("ERROR: Falló al seleccionar el menú '" + petType + "': " + e.getMessage());
            throw e;
        }
    }

    @And("Seleccionas el Product ID {string} correspondiente a {string}")
    public void seleccionas_el_product_id(String productId, String productName) {
        System.out.println(">>> Seleccionando Product ID: " + productId + " (" + productName + ")");
        try {
            pb.selectProductById(productId);
        } catch (Exception e) {
            System.err.println("ERROR: Falló al seleccionar productId '" + productId + "': " + e.getMessage());
            throw e;
        }
    }

    @And("Le das clic en \"Add to Cart\" para el ítem {string}")
    public void le_das_clic_add_to_cart_para_el_item(String itemDescription) {
        System.out.println(">>> Añadiendo al carrito: " + itemDescription);
        try {
             pb.addToCart(itemDescription);
        } catch (Exception e) {
            System.err.println("ERROR: Falló al hacer Add to Cart para '" + itemDescription + "': " + e.getMessage());
            throw e;
        }
    }

    @And("Le das clic en \"Proceed to Checkout\"")
    public void le_das_clic_en_proceed_to_checkout() {
        System.out.println(">>> Click en Proceed to Checkout...");
        try {
            pb.proceedToCheckout();
        } catch (Exception e) {
            System.err.println("ERROR: Falló al hacer Proceed to Checkout: " + e.getMessage());
            throw e;
        }
    }

    @Then("Se muestra la página de inicio de sesión solicitando usuario y contraseña")
    public void se_muestra_la_pagina_de_inicio_de_sesion() {
        System.out.println(">>> Validando que aparezca la página de Login (Sign In)...");
        try {
            Assert.assertTrue("No se muestra la página de login (Sign In)", pb.isLoginPageDisplayed());
        } catch (AssertionError ae) {
            System.err.println("ASSERTION FAILED: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            System.err.println("ERROR: al validar la página de login: " + e.getMessage());
            throw e;
        }
    }
}

