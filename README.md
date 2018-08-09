## Mancala game

### Problem
1. To create a mancala game with a POST with HTTP 201 and a URL for the game with id
2. To make moves for 2 players through PUT with HTTP 200 containing gameID and pit id as path variables and returning the status of the game with it.

### Solution

- The solution is Spring boot based and considers the following things
- Embedded Mongo DB has been considered for document store.
- Document has been structured around JSON
- REST endpoints are strictly JSON content processing
- Input validation has been programmatically done.
- Proper DB isolation has been considered using transansactional isolation level.
- Exception handler has been put to place.
- Custom REST error has been created to handle system exceptions and HTTP errors
- Focus has been given both on integration testing and unit testing.
- Documentation has been created using Spring RESTDocs and JUnit.
- Code itself is the documentation here.
- ASCIIDoctor style code API doc generation has been done.

### How to run build and run the app

- **Required things :** 
Java 8 ,  
Maven  and (optionally an IDE)

- **To build and package use :** _mvn clean package_ 
(you need to be connected to the internet for downloading the dependencies)

- **Once the app is build** and packaged (which also means test were run unless you chose to skip them)
 under _target/generated-docs/api-doc-html_ you would find valuable soure for test scenarios and example requests and responses.
  
- **To run use :** _java -jar target/game-mancala-0.0.1-SNAPSHOT.jar_
  
