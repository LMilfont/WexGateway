# WexGateway
Purchases Transaction Gateway for WEX

This application works as a gateway for incoming purchases transactions.

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





