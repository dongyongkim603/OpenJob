Project Title:
OPENJOB

Libraries Used:
Maven.Apache
Spring Boot 2.3.4
JUnit.vintage
WebJars
Jakarta.Validation
MariaDB.jdbc
Spring Security
Spring boot - maven plugin

Features:
UserDTO validation
multipartFile conversion
CRUD operations for ReviewDTO and JobDTO
Spring security user authentication
Google Map REST API consumption
Custom JPA search Queries
JUnit Tests for custom service layer methods
Dynamic Thymeleaf Html pages and fragments

Installation:
Project is made to be run on an Eclipse IDE environment and should be opened there. 
The project should run a Maven update to pull all the listed dependancies and plugins that are listed in the pom.xml file
onto local machine.
The application.properties file should be configured to the local machies MariaDB port, admin, and password.
MariaDB should create a schema that is named "openjob".
Start the project as a SpringBoot application from the Ecplise environment this will initalize all of the models as entities in the schema.
Open a web browser to the listed ip address (the default is: " localhost:8080 ").

Developers:
John Haney