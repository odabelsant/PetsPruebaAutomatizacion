package utils;

import org.openqa.selenium.By;

public class LocatorUtils {

    // Localizador por linkText simple
    public static By menuLink(String menuName){
        return By.linkText(menuName);
    }

    // Localizador para "Add to Cart" dado el texto descriptivo del ítem
    public static By addToCartByDescription(String description) {
        return By.xpath("//a[contains(text(),'" + description + "')]/../..//a[text()='Add to Cart']");
    }

    // Localizador genérico de botón/enlace por texto
    public static By buttonByText(String text) {
        return By.xpath("//a[contains(text(),'" + text + "')]");
    }

    // Localizador para buscar product links dentro del catálogo por productId
    public static By productLinkInCatalogLocator(String productId) {
        return By.xpath("//*[@id='Catalog']//a[contains(normalize-space(.), '" + productId + "')]");
    }

    // Localizador xpath exacto para links por texto (normalize-space)
    public static By xpathExactForLink(String text) {
        return By.xpath("//a[normalize-space()='" + text + "']");
    }

    // Localizador xpath que contiene el texto (partial)
    public static By xpathContainsForLink(String text) {
        return By.xpath("//a[contains(normalize-space(.), '" + text + "')]");
    }

    // Localizador del table del catalog (usado en verificación de categoría)
    public static By catalogTable() {
        return By.xpath("//*[@id='Catalog']/table");
    }
}
