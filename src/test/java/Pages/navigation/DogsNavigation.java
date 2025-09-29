package Pages.navigation;

import org.openqa.selenium.By;
import utils.LocatorUtils;

/**
 * Implementación para la categoría Dogs.
 */
public class DogsNavigation extends AbstractPetNavigation {

    private By bulldogLink = By.xpath("//*[@id='Catalog']/table/tbody/tr[2]/td[1]/a");
    private By maleAdultBulldogAddToCart = By.xpath("//*[@id='Catalog']/table/tbody/tr[2]/td[5]/a");

    @Override
    public void selectProductById(String productId) {
        if (productId == null) throw new IllegalArgumentException("productId no puede ser null");

        if (productId.equalsIgnoreCase("K9-BD-01") ||
                productId.equalsIgnoreCase("D-DB-01") ||
                productId.equalsIgnoreCase("K9-BD01")) {
            if (isElementPresent(bulldogLink)) {
                clickElement(bulldogLink);
                return;
            }
        }

        By productLinkInCatalog = LocatorUtils.productLinkInCatalogLocator(productId);
        if (isElementPresent(productLinkInCatalog)) {
            clickElement(productLinkInCatalog);
            return;
        }

        By generic = By.xpath("//a[contains(normalize-space(.), '" + productId + "')]");
        clickElement(generic);
    }

    @Override
    public void addToCart(String itemDescription) {
        if (itemDescription == null) throw new IllegalArgumentException("itemDescription no puede ser null");

        if (itemDescription.equalsIgnoreCase("Male Adult Bulldog") || itemDescription.equalsIgnoreCase("Adult Bulldog")) {
            clickElement(maleAdultBulldogAddToCart);
            return;
        }

        By fallback = LocatorUtils.addToCartByDescription(itemDescription);
        if (isElementPresent(fallback)) {
            clickElement(fallback);
            return;
        } else {
            throw new IllegalArgumentException("Descripción no soportada en Dogs: " + itemDescription);
        }
    }
}




