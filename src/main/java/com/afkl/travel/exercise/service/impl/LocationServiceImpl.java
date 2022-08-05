package com.afkl.travel.exercise.service.impl;

import com.afkl.travel.exercise.dto.LocationResponseDto;
import com.afkl.travel.exercise.mapper.LocationMapper;
import com.afkl.travel.exercise.model.LocationType;
import com.afkl.travel.exercise.repository.LocationRepository;
import com.afkl.travel.exercise.resolver.LocaleResolver;
import com.afkl.travel.exercise.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocaleResolver localeResolver;
    private final LocationMapper mapper;

    @Override
    @Transactional
    public List<LocationResponseDto> findByTypeAndCode(HttpServletRequest request, String type, String code) {
        var resolvedLocale = localeResolver.resolveLocale(request);
        var language = resolvedLocale.getLanguage().toUpperCase();
        var locations = locationRepository
                .findByLanguageAndTypeAndCode(LocationType.valueOf(type), code, language);
        return locations.stream()
                .map(location -> mapper.toDto(location, language))
                .collect(Collectors.toList());
    }
}
