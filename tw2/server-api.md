## Server API Documentation

#### API:

_Note: Since this is RMI, all methods may throw RemoteException_

```java
boolean register(UserProfileDto user, AuthProviderType authProviderType);
long login(String email, String password);
void logout(long token);
void createTrainingSession(long token, TrainingSessionDto trainingSession);
List<TrainingSessionDto> getTrainingSessions(long token);
TrainingSessionDto getTrainingSession(long token, UUID trainingSessionId);
void setUpChallenge(long token, ChallengeDto challenge);
List<ChallengeDto> downloadActiveChallenges(long token);
void acceptChallenge(long token, UUID challengeId);
List<ChallengeStatusDto> checkChallengesStatus(long token);
```



#### DTOs:

_Note: All DTOs contain as well serialVersionUID_

**UserProfileDto** 

```java
String email;
String name;
String birthDate;
int weight;
int height;
int maxHearthRate;
int restHeartRate;
```



**TrainingSessionDto**

```java
UUID id;
String title;
String startTime;
Date startDate;
SportType sportType;
float distance;
```



**ChallengeDTO**

```java
UUID id;
String name;
String target;
Date startDate;
Date endDate;
SportType sportType;
ChallengeStatusDto challengeStatus;
```



**ChallengeStatusDTO**

```java
float progress;
UUID challengeId;
```



#### Enums:

**AuthProviderType**

```java
GOOGLE, FACEBOOK
```

**SportType**

```java
RUNNING, CYCLING, RUNNING_CYCLING
```