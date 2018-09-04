# rate-limit-module
This module limits api call from requestor using configuration properties. Requestors are identified by ip addresses.

## Prerequisites
* JDK 1.8 or later
* Gradle 4+
* You can also import the code straight into your IDE: IntelliJ IDEA

## Configure

* Go to spring-boot-rate-limit-module\spring-boot-rate-limit\src\main\resources\application properties
* Specify rate limit. Default is 100
* Specify time limit. Default is 1 hour

## Installing
run the application using gradlew bootRun.  

```
gradlew bootRun. 
```
Now that the service is up, visit http://localhost:8080/welcome, where you see: Welcome!

## Running Integration tests
Run gradlew tests

```
gradlew tests
```

## Running manual tests on local

* Start using gradlew bootRun
* Visit api endpoint  http://localhost:8080/welcome
* Send request more than configured rate limit i.e if rate limit is 3 call send request >4 time to see rate limit exceed message

## Limitations and improvements:

* Only In-Memory version of rate limiter has been implemented though design is flexible to implement thirdparty enabled cache also.

