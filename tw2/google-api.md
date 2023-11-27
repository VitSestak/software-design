## Google Server API Documentation

#### API:

```java
GET /verify 
request body: VerificationRequest
consumes: "application/json"
returns: boolean    

POST /login
request body: LoginRequest 
consumes: "application/json"
returns: boolean    
```



#### Schemas:

**VerificationRequest** 

```java
String email;
```



**LoginRequest**

```java
String email;
String password;
```
