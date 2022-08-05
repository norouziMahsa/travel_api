package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.dto.LocationResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TranslationService {

    List<LocationResponseDto> findAll(HttpServletRequest language);
}
