package com.afkl.travel.exercise.resolver;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class LocaleResolver extends AcceptHeaderLocaleResolver {

    private static final List<Locale> SUPPORTED_LOCALES = Arrays
            .asList(new Locale("en"), new Locale("nl"));

    /**
     * returns the appropriate local based on Accept-Language header
     * If not specified or the requested language is not supported, english should be returned
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");
        if (language == null || language.isEmpty()) {
            return Locale.getDefault();
        }
        List<Locale.LanguageRange> list;
        try {
            list = Locale.LanguageRange.parse(language);
        } catch (IllegalArgumentException exception) {
            return Locale.getDefault();
        }
        Locale locale = Locale.lookup(list, SUPPORTED_LOCALES);
        if (locale == null) {
            return Locale.getDefault();
        }
        return locale;
    }
}
