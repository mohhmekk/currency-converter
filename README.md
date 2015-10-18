# Currency Converter

[![Build Status](https://travis-ci.org/mohhmekk/currency-converter.svg)](https://travis-ci.org/mohhmekk/currency-converter)


Currency Converter application that uses one of the public currency converter APIs, The application is preconfigured to use [Currency Layer](https://currencylayer.com) and [Open exchange rates] (https://openexchangerates.org).

Application configurations are managed through the property file `exchange.properties`, while Spring configurations are in the file `application.properties`.

Application is built using the following technologies:

**Backend**

- Java 8
- Spring (Boot, MVC, Security, Data)
- MongoLab

**Frontend**

- Node
- AngularJS, RequireJS and JQuery

**Build**

- Maven
- bower

**Testing**

- Junit and Spring-test for unit testing.
- Cucumber and selenium for acceptance testing.

Running the application
---
**Prerequisites**

1. Java 8
2. Node
3. bower
4. Maven 3

**Build and Run**

1. First you need to download the front end dependencies using the bower:

		bower install

2. Build the source code delivered in /currency-exchange by running:   
		
		mvn clean install 
   from the same folder (currency-exchange)

3. To start the application using in-memory database (mongo)
		
		mvn clean install spring-boot:run -Dspring.profiles.active=test

4. To start the application using a cloud based mongo database
		
		mvn clean install spring-boot:run -Dspring.profiles.active=development

5. After that you can access the application using the following URL:
        
        http://localhost:8080

6. To login in the application the following user is pre-registered:
        
        username/password: test123/Password1   
