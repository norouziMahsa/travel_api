package com.afkl.travel.exercise.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TranslationRepositoryTest {

    @Autowired
    TranslationRepository translationRepository;

    @Test
    public void testFindAllByLanguageWithValidInput() {
        // Given
        var language = "EN";

        // When
        var translations = translationRepository.findAllByLanguage(language);

        // Then
        assertNotNull(translations);
        assertTrue(translations.size() > 0);
        assertEquals(language, translations.get(0).getLanguage());
    }

    @Test
    public void testFindAllByLanguageWithInvalidInput() {
        // Given
        var language = "invalid language";

        // When
        var translations = translationRepository.findAllByLanguage(language);

        // Then
        assertEquals(0, translations.size());
    }
}
