The design of my program helps me with the use of lambda expressions and anonymous classes for testing:

Dependency injection: The searchRecipes method allows the DataService dependency as a parameter 
instead of instantiating it internally. This allows testers to inject mock implementations during testing.

Interfaces: By depending on the DataService interface through the getRecipes() method instead of 
concrete implementations such as SqliteDataService, testers can provide any implementation that satisfies the interface contract.

Polymorphism: The interface allows for polymorphic behavior: during normal execution we use SqliteDataService,
while tests can substitute different implementations that behave differently but it implements to the same interface.

Couple/decouple: The design decouples the searchRecipes logic from specific data sources. 
This separation of concerns makes the code easier to maintain and test, as changes to the data source implementation 
do not affect the search functionality.

