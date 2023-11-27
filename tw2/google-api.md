## Google Server API Documentation

#### API:

```java
GET /verify 
request body: VerificationRequest
consumes: "application/json"
returns: boolean
status code: 
	- 200 OK
    - 400 Bad request    
        
POST /login
request body: LoginRequest 
consumes: "application/json"
returns: boolean    
status code: 
	- 200 OK
    - 400 Bad request
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
