package com.afkl.travel.exercise.service.impl;

import com.afkl.travel.exercise.dto.LocationResponseDto;
import com.afkl.travel.exercise.mapper.LocationMapper;
import com.afkl.travel.exercise.repository.TranslationRepository;
import com.afkl.travel.exercise.resolver.LocaleResolver;
import com.afkl.travel.exercise.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;
    private final LocationMapper mapper;
    private final LocaleResolver localeResolver;

    @Override
    @Transactional
    public List<LocationResponseDto> findAll(HttpServletRequest request) {
        var resolvedLocale = localeResolver.resolveLocale(request);
        var language = resolvedLocale.getLanguage().toUpperCase();
        var translations = translationRepository
                .findAllByLanguage(language);
        return translations
                .stream()
                .map(translation -> mapper.toDto(translation.getLocation(), language))
                .collect(Collectors.toList());
    }
}
