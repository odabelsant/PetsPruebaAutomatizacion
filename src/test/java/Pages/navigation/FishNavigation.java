package Pages.navigation;

import org.openqa.selenium.By;
import utils.LocatorUtils;

/**
 * Implementación para la categoría Fish.
 */
public class FishNavigation extends AbstractPetNavigation {

    private By angelfishLink = By.xpath("//*[@id='Catalog']/table/tbody/tr[2]/td[1]/a");
    private By largeAngelfishAddToCart = By.xpath("//*[@id='Catalog']/table/tbody/tr[2]/td[5]/a");

    @Override
    public void selectProductById(String productId) {
        if (productId == null) throw new IllegalArgumentException("productId no puede ser null");

        if (productId.equalsIgnoreCase("FI-SW-01")) {
            if (isElementPresent(angelfishLink)) {
                clickElement(angelfishLink);
                return;
            }
        }

        By productLinkInCatalog = LocatorUtils.productLinkInCatalogLocator(productId);
        if (isElementPresent(productLinkInCatalog)) {
            clickElement(productLinkInCatalog);
            return;
        }

        By productLink = By.xpath("//a[contains(normalize-space(.),'" + productId + "')]");
        clickElement(productLink);
    }

    @Override
    public void addToCart(String itemDescription) {
        if (itemDescription == null) throw new IllegalArgumentException("itemDescription no puede ser null");

        if (itemDescription.equalsIgnoreCase("Large Angelfish")) {
            clickElement(largeAngelfishAddToCart);
            return;
        }

        By fallback = LocatorUtils.addToCartByDescription(itemDescription);
        if (isElementPresent(fallback)) {
            clickElement(fallback);
            return;
        } else {
            throw new IllegalStateException("Descripción no soportada en Fish: " + itemDescription);
        }
    }
}
