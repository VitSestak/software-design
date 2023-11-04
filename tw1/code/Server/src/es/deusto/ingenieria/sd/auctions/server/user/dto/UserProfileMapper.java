package es.deusto.ingenieria.sd.auctions.server.user.dto;

import es.deusto.ingenieria.sd.auctions.server.user.model.UserProfile;

public class UserProfileMapper {

    public UserProfileDto userProfileToDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                .email(userProfile.getEmail())
                .name(userProfile.getName())
                .birthDate(userProfile.getBirthDate())
                .weight(userProfile.getWeight())
                .height(userProfile.getHeight())
                .maxHearthRate(userProfile.getMaxHearthRate())
                .restHeartRate(userProfile.getRestHeartRate())
                .build();
    }
}
