package com.afkl.travel.exercise.mapper;

import com.afkl.travel.exercise.dto.LocationResponseDto;
import com.afkl.travel.exercise.model.Location;
import com.afkl.travel.exercise.model.LocationType;
import com.afkl.travel.exercise.model.Translation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class LocationMapperTest {

    @InjectMocks
    LocationMapper mapper;

    @Test
    public void toDtoTest() {
        // Given
        var language = "NL";
        var expectedName = "Nederland";
        var expectedDescription = "Nederland (NL)";

        var location = Location.builder()
                .id(4614)
                .code("NL")
                .type(LocationType.country)
                .longitude(5.45)
                .latitude(52.3)
                .translations(new ArrayList<>())
                .parent(null)
                .build();

        var enTranslation = Translation.builder()
                .id(7131)
                .location(location)
                .language("EN")
                .name("Netherlands")
                .description("Netherlands (NL)")
                .build();

        var nlTranslation = Translation.builder()
                .id(7132)
                .location(location)
                .language("NL")
                .name("Nederland")
                .description("Nederland (NL)")
                .build();

        location.getTranslations().addAll(Arrays.asList(enTranslation, nlTranslation));

        // When
        LocationResponseDto dto = mapper.toDto(location, language);

        // Then
        assertNotNull(dto);
        assertEquals(expectedName, dto.getName());
        assertEquals(expectedDescription, dto.getDescription());
    }
}
