Feature: PetManagement

  Background:
    Given Accedes a la pagina "https://petstore.octoperf.com/actions/Catalog.action"

   Scenario Outline: Compra de Mascota
     When Le das clic en el menú superior "<Pet>"
     And Seleccionas el Product ID <ProductId> correspondiente a <PetType>
     And Le das clic en "Add to Cart" para el ítem <SidePet>
     And Le das clic en "Proceed to Checkout"
     Then Se muestra la página de inicio de sesión solicitando usuario y contraseña
     Examples:
       | Pet |ProductId  | PetType  | SidePet |
       | Fish | "FI-SW-01" | "Angelfish" | "Large Angelfish" |
       | Dogs | "D-DB-01" | "Bulldog" | "Adult Bulldog" |
