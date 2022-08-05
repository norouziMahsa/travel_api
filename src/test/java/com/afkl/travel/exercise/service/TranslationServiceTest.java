package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.mapper.LocationMapper;
import com.afkl.travel.exercise.model.Location;
import com.afkl.travel.exercise.model.LocationType;
import com.afkl.travel.exercise.model.Translation;
import com.afkl.travel.exercise.repository.TranslationRepository;
import com.afkl.travel.exercise.resolver.LocaleResolver;
import com.afkl.travel.exercise.service.impl.TranslationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class TranslationServiceTest {

    @InjectMocks
    TranslationServiceImpl translationService;

    @Mock
    TranslationRepository translationRepository;

    @Mock
    LocaleResolver localeResolver;

    @Mock
    LocationMapper mapper;


    @Test
    public void testFindAll() {
        // Given
        var request = new MockHttpServletRequest();
        var type = "country";
        var code = "NL";

        var location = Location.builder()
                .id(4614)
                .code("NL")
                .type(LocationType.country)
                .longitude(5.45)
                .latitude(52.3)
                .translations(new ArrayList<>())
                .parent(null)
                .build();

        var translation = Translation.builder()
                .id(7131)
                .location(location)
                .language("NL")
                .name("Netherlands")
                .description("Netherlands (NL)")
                .build();

        location.getTranslations().add(translation);

        when(localeResolver.resolveLocale(request)).thenReturn(new Locale("NL"));
        when(translationRepository.findAllByLanguage(any())).thenReturn(Collections.singletonList(translation));
        when(mapper.toDto(any(), any())).thenCallRealMethod();

        // When
        var dto = translationService.findAll(request);

        // Then
        assertNotNull(dto);
        assertTrue(dto.size() > 0);
        assertEquals(translation.getName(), dto.get(0).getName());
        assertEquals(location.getCode(), dto.get(0).getCode());
    }
}
