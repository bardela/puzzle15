# Puzzle15

## Prerequisites

Java >= 21

```
sudo apt-get update
sudo apt install default-jdk
```

Maven is installation is optional since a maven wrapper is part of this repo
``mvnw`` and ``mvnw.cmd``

```
sudo apt install maven
```

## Config

Client Authentication is protected by API-Key.
Set up the desired values in `application.properties`

```
auth.client.header=X-API-Key
auth.client.key=SomeKeyMoreSecureThanThis
```

## Tests
Using the wrapper
```
./mvnw clean
```

## Run in Dev

Use your IDE to
* start on `com.ardela.puzzle15.Puzzle15Application`
* set profile to `dev`
* set ENV variables for preloading a _REGULAR_ user and a _ADMIN_ user


or run the script
```
PRELOAD_USER_USERNAME=john PRELOAD_USER_PASSWORD=1234 PRELOAD_ADMIN_USERNAME=admin PRELOAD_ADMIN_PASSWORD=5678 mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
The users `REGULAR_USER` and `ADMIN_USER`
will be added as users in order to use the app

### Env variables
```
PRELOAD_USER_USERNAME // username for the REGULAR user
PRELOAD_USER_PASSWORD
PRELOAD_ADMIN_USERNAME // username for the ADMIN user
PRELOAD_ADMIN_PASSWORD
```
Used for adding a Regular user and an Admin when running with dev profile

### Properties
In `application.properties`
```
auth.client.header=X-API-Key
auth.client.key=SomeKeyMoreSecureThanThis
```
They are used for client authentication
Every request should include it.

### Examples of requests

#### Create a Game:
```
curl --location --request POST 'http://localhost:8080/games/' \
-H 'Content-Type: application/json' \
-H 'X-API-Key: SomeKeyMoreSecureThanThis' \
-H 'Authorization: Basic am9objoxMjM0'
```
Will receive the Game data in json format


#### Move to the left in game with id 1
```
curl --location --request POST 'http://localhost:8080/games/1/move' \
-H 'Content-Type: application/json' \
-H 'X-API-Key: SomeKeyMoreSecureThanThis' \
-H 'Authorization: Basic am9objoxMjM0' \
--data-raw '{"move":"LEFT"}'
```
Will receive the Game data after performing the move


## Decisions about Implementation
### Spring Boot
Chose the framework cause it makes it easier to create APIs with its annotations, dependencies and tools 

### Authentication
The game has 2 levels of authentication
1. Client Authentication (api-key)
2. User Authentication (Basic Authentication)

Decided to implement **client authentication** in order to protect the API from unknown clients.
Of course in a real production environment, there would be different keys for clients and they could be renewed or revoked.

I added **Basic Authentication** to prevent other users than the _owner_ of the game to play his/her game. Again, this
is just an example, but in a real world, we would have a more complex that would include Tokens and sessions.

I used the Springboot classes and methods for implementing these 2 methods to meet the minimum authentication requirements
of the exercise. They're located in `SecurityConfig.java` and `src/main/java/com/ardela/puzzle15/config/authentication/`


### Monolith
I considered having different microservices for Authentication and Game CRUD operations connected by
an API Gateway, but I found it a bit overkill for the size of the project.

### Functionality
Only the Game creation and move End points have been completed
Unfortunately, I didn't have more time to adding a new end point for terminating a Game by an admin, but
the ground work is there, except implementing a Producer with the message for RabbitMQ. 
See `//TODO` in `GameController.java`

### Test Coverage
Just covered a few cases when creating a game due to lack of time.
. See `//TODO` in `GameControllerTests.java`


