package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.model.LocationType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @Test
    public void testFindByLanguageAndTypeAndCodeWithValidInputs() {
        // Given
        var language = "NL";
        var type = "country";
        var code = "NL";

        // When
        var locations = locationRepository.findByLanguageAndTypeAndCode(LocationType.valueOf(type), code, language);

        // Then
        assertNotNull(locations);
        assertEquals(code, locations.get(0).getCode());
        assertNotNull(locations.get(0).getTranslations());
    }

    @Test
    public void testFindByLanguageAndTypeAndCodeWithInvalidInputs() {
        // Given
        var language = "invalid language";
        var type = "city";
        var code = "AMS";

        // When
        var locations = locationRepository.findByLanguageAndTypeAndCode(LocationType.valueOf(type), code, language);

        // Then
        assertTrue(locations.isEmpty());
    }
}
