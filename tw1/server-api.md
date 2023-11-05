## Server API Documentation

#### API:

_Note: Since this is RMI, all methods may throw RemoteException_

```java
boolean googleRegistration(UserProfileDto user);
boolean facebookRegistration(UserProfileDto user);
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

##### Additional logic:

```java
// get forecast from Klimat
public WeatherDto getWeatherForecast(long token, Date date, String location);

// from cycling computers
public void saveActivityRecord(long token, ActivityRecordDTO activityRecord);

// exchange data with other sports apps
public TrainingDataDto exchangeTrainingSessionData(long token, String appId);
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
```



**ChallengeStatusDTO**

```java
int progress;
String challengeName;
```



**TrainingDataDto**

- out of scope

  

**ActivityRecordDTO**

- out of scope

  

**WeatherDto**

- out of scope

  

#### Enums:

**AuthProviderType**

```java
GOOGLE, FACEBOOK
```

**SportType**

```java
RUNNING, CYCLING, RUNNING_CYCLING
```