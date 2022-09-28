# Spring Cloud Study

Using Spring Cloud Eureka

### Project Structure (What Spring Cloud features were used)

- **_server  \[7001 / 7002\]_**
  - Eureka Server
- **_provider \[8001 / 8002 / 8003/ 8004\]_**
  - Eureka Client
  - Content provider
- **_consumer \[9001\]_**
  - Eureka Client
  - Hystrix
  - OpenFeign
- **_config-server \[8888\]_**
  - ConfigServer
- **_config-client \[8889\]_**
  - ConfigClient

## Introduction to this repository

Study the basic components of Spring Cloud Netflix.


## Eureka
Eureka, one of the components of [Spring Cloud Netflix](https://spring.io/projects/spring-cloud-netflix), is a service discovery, which seperated with server-side and client-side. 

### What is Service Discovery
Typically, we will hard-code the hostname and port into properties file to access another system. conventional SOA (Service Oriented Architecture) ecosystem, services’ network locations would hardly change, as they are deployed in on-premise data centers. <br/>
However, this approach is nearly impossible in a cloud based microservice architecture as increased number of services, dynamically assigned network locations.

Reference:
<br/>[Introduction to Spring Cloud Netflix – Eureka (Baeldung)](https://www.baeldung.com/spring-cloud-netflix-eureka)
<br/>[Spring Cloud: Service Discovery With Eureka](https://medium.com/swlh/spring-cloud-service-discovery-with-eureka-16f32068e5c7)