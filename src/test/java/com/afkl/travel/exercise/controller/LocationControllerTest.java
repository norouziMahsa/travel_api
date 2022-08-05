package com.afkl.travel.exercise.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LocationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Value("${locations.base.path}")
    String locationsBasePath;

    @Value("${application.user}")
    String applicationUser;

    @Value("${application.password}")
    String applicationPassword;

    @Test
    public void testGetAllLocationsWithoutLanguageHeader() throws Exception {
        // When/Then
        mvc.perform(MockMvcRequestBuilders
                .get(locationsBasePath)
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // should use default language EN and return result
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllLocationsWithValidLanguageHeader() throws Exception {
        // Given
        var acceptLanguage = "EN";
        // When/Then
        mvc.perform(MockMvcRequestBuilders
                .get(locationsBasePath)
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader())
                .header(HttpHeaders.ACCEPT_LANGUAGE, acceptLanguage)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllLocationsWithInvalidLanguageHeader() throws Exception {
        // Given
        var acceptLanguage = "invalid language";
        // When/Then
        mvc.perform(MockMvcRequestBuilders
                .get(locationsBasePath)
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader())
                .header(HttpHeaders.ACCEPT_LANGUAGE, acceptLanguage)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllLocationsWithoutAuth() throws Exception {
        // Given
        var acceptLanguage = "invalid language";
        // When/Then
        mvc.perform(MockMvcRequestBuilders
                .get(locationsBasePath)
                .header(HttpHeaders.ACCEPT_LANGUAGE, acceptLanguage)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetLocationsByTypeAndCodeWithoutLanguageHeader() throws Exception {
        // Given
        var type = "country";
        var code = "NL";
        // When/Then
        mvc.perform(MockMvcRequestBuilders
                .get(locationsBasePath + "/" + type + "/" + code)
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].type").value(type))
                .andExpect(jsonPath("[0].code").value(code));
    }

    @Test
    public void testGetLocationsByTypeAndCodeWithLanguageHeader() throws Exception {
        // Given
        var acceptLanguage = "NL";
        var type = "country";
        var code = "NL";
        var expectedCountryName = "Nederland";
        // When/Then
        mvc.perform(MockMvcRequestBuilders
                .get(locationsBasePath + "/" + type + "/" + code)
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader())
                .header(HttpHeaders.ACCEPT_LANGUAGE, acceptLanguage)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].type").value(type))
                .andExpect(jsonPath("[0].code").value(code))
                .andExpect(jsonPath("[0].name").value(expectedCountryName));
    }

    @Test
    public void testGetLocationsByTypeAndCodeWithInvalidType() throws Exception {
        // Given
        var type = "invalid type";
        var code = "NL";
        // When/Then
        mvc.perform(MockMvcRequestBuilders
                .get(locationsBasePath + "/" + type + "/" + code)
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetLocationsByTypeAndCodeWithoutAuth() throws Exception {
        // Given
        var type = "city";
        var code = "NL";
        // When/Then
        mvc.perform(MockMvcRequestBuilders
                .get(locationsBasePath + "/" + type + "/" + code)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    private Object getBasicAuthHeader() {
        return "Basic " + Base64.getEncoder().encodeToString((applicationUser + ":" + applicationPassword).getBytes());
    }
}
