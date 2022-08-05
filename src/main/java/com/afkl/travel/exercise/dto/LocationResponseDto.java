package com.afkl.travel.exercise.dto;

import com.afkl.travel.exercise.model.LocationType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDto {
    private String code;
    private String name;
    private LocationType type;
    private Double latitude;
    private Double longitude;
    private String description;
    private String parentCode;
    private LocationType parentType;
}
