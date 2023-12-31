package es.deusto.ingenieria.sd.auctions.server.user.dto;

import es.deusto.ingenieria.sd.auctions.server.user.model.UserProfile;

public class UserProfileMapper {

    private static UserProfileMapper instance;

    private UserProfileMapper() {}

    public static synchronized UserProfileMapper getInstance() {
        if (instance == null) {
            instance = new UserProfileMapper();
        }
        return instance;
    }

    public UserProfileDto userProfileToDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                             .email(userProfile.getEmail())
                             .name(userProfile.getName())
                             .birthDate(userProfile.getBirthDate())
                             .weight(userProfile.getWeight())
                             .height(userProfile.getHeight())
                             .maxHeartRate(userProfile.getMaxHeartRate())
                             .restHeartRate(userProfile.getRestHeartRate())
                             .build();
    }

    public UserProfile dtoToUserProfile(UserProfileDto userProfileDto) {
        return UserProfile.builder()
                          .email(userProfileDto.getEmail())
                          .name(userProfileDto.getName())
                          .birthDate(userProfileDto.getBirthDate())
                          .weight(userProfileDto.getWeight())
                          .height(userProfileDto.getHeight())
                          .maxHeartRate(userProfileDto.getMaxHeartRate())
                          .restHeartRate(userProfileDto.getRestHeartRate())
                          .build();
    }
}
