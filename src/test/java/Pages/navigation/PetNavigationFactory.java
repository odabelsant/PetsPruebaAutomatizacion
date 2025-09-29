package Pages.navigation;

public class PetNavigationFactory {
    public static AbstractPetNavigation getNavigation(String petType) {
        if (petType == null) throw new IllegalArgumentException("petType no puede ser null");
        switch (petType.trim().toLowerCase()) {
            case "fish":
                return new FishNavigation();
            case "dogs":
            case "dog":
                return new DogsNavigation();
            default:
                throw new IllegalArgumentException("Pet type no soportado: " + petType);
        }
    }
}
