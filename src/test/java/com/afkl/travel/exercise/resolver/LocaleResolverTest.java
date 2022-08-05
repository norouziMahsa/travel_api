package com.afkl.travel.exercise.resolver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class LocaleResolverTest {

    @InjectMocks
    LocaleResolver localeResolver;

    @Test
    public void testResolveLocaleWithSupportedLanguageHeader() {
        // Given
        var language = "NL";
        var request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", language);

        // When
        Locale locale = localeResolver.resolveLocale(request);

        // Then
        assertNotNull(locale);
        assertEquals(language, locale.getLanguage().toUpperCase());
    }

    @Test
    public void testResolveLocaleWithUnsupportedLanguageHeader() {
        // Given
        var unsupportedLanguage = "unsupported language";
        var expectedLanguageReturnByResolver = "EN";
        var request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", unsupportedLanguage);

        // When
        Locale locale = localeResolver.resolveLocale(request);

        // Then
        assertNotNull(locale);
        assertEquals(expectedLanguageReturnByResolver, locale.getLanguage().toUpperCase());
    }
}
