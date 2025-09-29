🐾 PetsPruebaAutomatizacion
Automatización de pruebas funcionales para la tienda de mascotas en línea JPetStore, desarrollada con Java, Selenium WebDriver, Cucumber y Gherkin en IntelliJ IDEA.

📋 Descripción
Este proyecto automatiza escenarios clave del catálogo de productos de JPetStore, validando la correcta visualización de mascotas, descripciones, precios y navegación por categorías. Las pruebas están escritas en lenguaje Gherkin y permiten verificar el comportamiento esperado desde la perspectiva del usuario final.

🧰 Tecnologías utilizadas
Java 17
Selenium WebDriver
Cucumber
Gherkin
JUnit
IntelliJ IDEA CE
Maven
📁 Estructura del proyecto
PetsPruebaAutomatizacion/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Config/
│   │       ├── Hooks/
│   │       ├── Pages/
│   │       └── interfaces/
│   └── test/
│       └── java/
│           └── StepDefinitions/
├── resources/
│   └── PetManagement/
│       └── Pet.feature
├── pom.xml
└── README.md

🧪 Escenarios automatizados
El archivo Pet.feature contiene escenarios como:

Compra de mascota por categoría (Fish, Dogs)
Validación de navegación desde el catálogo hasta el login
Verificación de elementos visibles en la interfaz

Ejemplo de escenario en Gherkin:
CucumberScenario Outline: Compra de Mascota  Given el usuario navega a la categoría <PetType>  When selecciona el producto con ID <ProductID>  And añade la mascota <SidePet> al carrito  Then se muestra la pantalla de loginExamples:| PetType | ProductID | SidePet || Fish    | FI-SW-01  | AngelFish |  | Dogs    | K9-BD-01  | Bulldog   |Mostrar más líneas

🚀 Cómo ejecutar las pruebas

Clona el repositorio:
Shellgit clone https://github.com/tuusuario/PetsPruebaAutomatizacion.gitMostrar más líneas

Abre el proyecto en IntelliJ IDEA.

Ejecuta los escenarios desde el archivo Pet.feature o desde las clases de Step Definitions.

📄 .gitignore recomendado
Incluye este archivo .gitignore para evitar subir archivos innecesarios:
.idea/
*.iml
target/
*.log
*.class
.DS_Store

🤝 Contribuciones
Las contribuciones son bienvenidas. Puedes abrir issues o enviar pull requests para mejorar los escenarios, agregar nuevas pruebas o refactorizar el código.

📌 Autor
Odabel Santos
Analista de Aseguramiento de Calidad en Insoltech
