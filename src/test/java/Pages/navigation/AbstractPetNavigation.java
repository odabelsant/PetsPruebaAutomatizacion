package Pages.navigation;

import Config.Config;
import interfaces.PageAction;
import interfaces.PetNavigation;
import org.openqa.selenium.By;
import utils.LocatorUtils;

/**
 * Clase abstracta con comportamiento común para categorías.
 * - Usa LocatorUtils para todos los localizadores.
 */
public abstract class AbstractPetNavigation extends Config implements PageAction, PetNavigation {

    // Locators comunes (definidos en cada clase/instancia cuando se necesite)
    protected By signInLink = By.linkText("Sign In");
    protected By proceedToCheckoutBtn = By.xpath("//*[@id='Cart']/a"); // este locator es específico del flujo y puede quedarse aquí
    protected By cartTable = By.cssSelector("table.cart");

    // Locators para navegar categorías (los locators específicos siguen en la clase)
    private By fishCategoryLocator1 = By.xpath("//*[@id='QuickLinks']/a[1]");
    private By fishCategoryLocator2 = By.xpath("//*[@id='SidebarContent']/a[1]");
    private By dogsCategoryLocator = By.xpath("//*[@id='QuickLinks']/a[2]");

    @Override
    public void proceedToCheckout() {
        proceedToCheckoutGeneric(proceedToCheckoutBtn, signInLink);
    }

    @Override
    public void validateMenu(String petType) {
        if ("Fish".equalsIgnoreCase(petType)) {
            validateMenuGeneric(petType, fishCategoryLocator1, fishCategoryLocator2);
            return;
        }
        if ("Dogs".equalsIgnoreCase(petType) || "Dog".equalsIgnoreCase(petType)) {
            validateMenuGeneric(petType, dogsCategoryLocator);
            return;
        }
        validateMenuGeneric(petType);
    }

    @Override
    public void clickOnMenu(String petType) {
        if ("Fish".equalsIgnoreCase(petType)) {
            clickMenuForPet(petType, fishCategoryLocator1, fishCategoryLocator2);
            return;
        }
        if ("Dogs".equalsIgnoreCase(petType) || "Dog".equalsIgnoreCase(petType)) {
            clickMenuForPet(petType, dogsCategoryLocator);
            return;
        }
        clickMenuForPet(petType);
    }

    @Override
    public void verifyCategoryPage(String petType) {
        verifyCategoryPageGeneric(petType);
    }

    // Métodos abstractos que las subclases deben implementar
    public abstract void selectProductById(String productId);
    public abstract void addToCart(String itemDescription);
}
