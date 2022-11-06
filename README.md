## Description
Blog-hibernate is an educational console CRUD application that has the following entities:

* Writer
* Post
* Label

Application uses Postgres as DBMS. To run application locally you need first to create the database manually
on your instance of Postgres and then specify DB info (path and credentials) in the files:

* POM.xml
* hibernate.cfg.xml

## Requirements
To build & run the application you need Java-11 and Maven-3.8.5 installed.

## Build and Run the Application
For initialization the database (creating required tables) it is necessary to run Maven command first:

`mvn clean flyway:migrate`

To launch the app just run Main.main() method in your IDE.