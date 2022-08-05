package com.afkl.travel.exercise.mapper;

import com.afkl.travel.exercise.dto.LocationResponseDto;
import com.afkl.travel.exercise.model.Location;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class LocationMapper {

    public LocationResponseDto toDto(Location location, String language) {
        var name = "";
        var description = "";
        if (!CollectionUtils.isEmpty(location.getTranslations())) {
            var foundTranslationByLanguage = location.getTranslations()
                    .stream()
                    .filter(translation -> translation.getLanguage().equals(language))
                    .findFirst();
            if (foundTranslationByLanguage.isPresent()) {
                name = foundTranslationByLanguage.get().getName();
                description = foundTranslationByLanguage.get().getDescription();
            }
        }
        return LocationResponseDto.builder()
                .code(location.getCode())
                .name(name)
                .type(location.getType())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .description(description)
                .parentCode(location.getParent() != null ? location.getParent().getCode() : null)
                .parentType(location.getParent() != null ? location.getParent().getType() : null)
                .build();
    }
}
