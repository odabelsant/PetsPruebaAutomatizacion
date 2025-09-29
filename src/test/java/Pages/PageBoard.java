package Pages;

import Config.Config;
import Pages.navigation.AbstractPetNavigation;
import Pages.navigation.PetNavigationFactory;
import org.openqa.selenium.By;

public class PageBoard extends Config {

    private AbstractPetNavigation nav;

    public void accessPageURL(String url) {
        startDriver();
        accessPageMain(url);
    }

    public void validateMenu(String petType) {
        AbstractPetNavigation tmp = PetNavigationFactory.getNavigation(petType);
        tmp.validateMenu(petType);
    }

    public void petSelect(String petType) {
        this.nav = PetNavigationFactory.getNavigation(petType);
        nav.clickOnMenu(petType);
    }

    public void selectProductById(String productId) {
        retryAction(this::ensureNavSelected, 2);
        nav.selectProductById(productId);
    }

    public void addToCart(String itemDescription) {
        ensureNavSelected();
        nav.addToCart(itemDescription);

        try {
            waitForClickableElement(By.xpath("//*[@id='Cart']/a"), 10);
        } catch (Exception e) {
            System.err.println("WARN: proceedToCheckout no apareció inmediatamente tras addToCart: " + e.getMessage());
        }
    }

    public void proceedToCheckout() {
        ensureNavSelected();
        nav.proceedToCheckout();
    }

    public void verifyCategoryPage(String petType) {
        ensureNavSelected();
        nav.verifyCategoryPage(petType);
    }

    private void ensureNavSelected() {
        if (this.nav == null) {
            throw new IllegalStateException("Navegación no inicializada. Llama antes a petSelect(petType).");
        }
    }

    public boolean isLoginPageDisplayed() {
        return isElementPresent(By.linkText("Sign In"));
    }
}



