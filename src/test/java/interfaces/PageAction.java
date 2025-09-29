package interfaces;

/**
 * Acciones genéricas de página relacionadas a producto / carrito.
 */
public interface PageAction {
    // Seleccionar producto por Product ID (ej. FI-SW-01, K9-BD-01)
    void selectProductById(String productId);

    // Agregar al carrito por descripción (ej. "Large Angelfish", "Male Adult Bulldog")
    void addToCart(String itemDescription);

    // Proceder al checkout desde el carrito
    void proceedToCheckout();
}
