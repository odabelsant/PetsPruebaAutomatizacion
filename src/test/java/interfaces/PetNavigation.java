package interfaces;

/**
 * Navegación por categorías (menú superior).
 */
public interface PetNavigation {
    void validateMenu(String petType);
    void clickOnMenu(String petType);
    void verifyCategoryPage(String petType);
}
