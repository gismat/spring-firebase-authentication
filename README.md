# Spring Firebase Authentication
Spring Boot monolithic JHipster generated starter. With firebase authentication implemented. 

## Introduction
I searched all over internet for this, but I did not find what I wanted. As a result, I decided to write myself. The classes of https://github.com/savicprvoslav/Spring-Boot-starter repository are used. These classes are adapted to the latest version (firebase-admin: 5.5.0). This is my first project on Spring Boot so there can be mistakes and bad practices. If you have ideas and improvements for this project, please send request. You can use it as you wish. 

Contact: jadmen94@gmail.com

## Usage
1. Add admin sdk json config to "config/firebase/********.json"
2. Define path of admin sdk json config on /config/firebase/FirebaseConfig.java:33
3. Define project firebase database url on /config/firebase/FirebaseResources.java:5

For Firebase authorization send request with X-Authorization-Firebase header to "api/firebaseauthenticate"




