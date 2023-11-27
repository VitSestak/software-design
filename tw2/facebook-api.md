## Facebook Server API Documentation

#### Socket Server API:

```java
port: 35600
object input stream:
	object: VerificationRequest | LoginRequest
data output stream:
	data: boolean
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
