package com.afkl.travel.exercise.controller;


import com.afkl.travel.exercise.dto.LocationResponseDto;
import com.afkl.travel.exercise.service.LocationService;
import com.afkl.travel.exercise.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${locations.base.path}")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final TranslationService translationService;

    @GetMapping
    public List<LocationResponseDto> getAll(HttpServletRequest request) {
        return translationService.findAll(request);
    }

    @GetMapping("/{type}/{code}")
    public List<LocationResponseDto> getByTypeAndCode(
            HttpServletRequest request,
            @PathVariable("type") String type,
            @PathVariable("code") String code) {
        return locationService.findByTypeAndCode(request, type, code);
    }
}
