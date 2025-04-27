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


