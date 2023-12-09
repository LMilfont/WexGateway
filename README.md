# WexGateway
Purchases Transaction Gateway for WEX

This application works as a gateway for incoming purchases transactions.

# Screenshots

![alt text](https://github.com/LMilfont/WexGateway/blob/main/screenshots/Screenshot01.png?raw=false)

# Features

* Accepts incoming purchase transactions from HTTP JSON/REST requests
* Stores received transactions as JSON objects in a text-file
* Allows conversion of individual purchase transaction amount from US-Dollars to foreign currencies
* Gets foreign currencies exchange rate from official "Fiscal-Data-Treasury" web service

# Stored Data

* Description
* Transaction Date
* Purchase Amount
* Unique Identifier is dynamically created for each new record

# Technical Implementation

* Framework    : Java Spring Boot
* IDE          : Spring Tool Suite 4
* Packaging    : Gradle
* Persistance  : Standard Text File (JSON)
* Libraries    : GSON, okhttp3, 
* JAVA         : openjdk version "17.0.6"
* Tests        : JUnit
* Architecture : Model-View-Controller
* GUI          : Java Swing

# Requirements

* JAVA 17
* Windows / Linux / MacOS

# Running the Application

You may run this application by calling the executable JAR file, like this:

   java -jar WexGateway-0.0.1-SNAPSHOT.jar


# Installation of Development Environment

* Download “Spring Tools 4 for Eclipse” from https://spring.io/tools
* Extract file contents with ”java -jar spring-tool-suite-4-4.20.1.RELEASE-e4.29.0-win32.win32.x86_64.self-extracting.jar”
* Execute SpringToolSuite4.exe
* Clone this repository with "git clone https://github.com/LMilfont/WexGateway.git"
* Import Project on Spring Tools 4 with “Import Project”, then “Gradle > Existing Gradle Project”
* Run WexGatewayApplication.java as a Java Spring Boot Application

# How to use this application

While executing, the app will wait for http POST requests with purchase transactions.
Each time a valid request comes in, a new entry will appear on the app screen table.
The bottom of the screen will show details of a single purchase operation upon selection on the table.

You may use Postman to sen transactions, or any other preferred tool.


# Testing

Tests were done using JUnit, as can be seen in Screenshot04.
You may run JUnit teste by opening file "WecControllerTest.java" then choose "Run as JUnit Test".
 
 


