package com.ca.formation.formationdemo1.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerConfigTest {

    @Autowired
    private SwaggerConfig swaggerConfig;

    @Test
    @DisplayName("Doit retourner un objet openapi avec un titre correcte")
    void springShopCorrectTitle() {
        assertEquals("Formtion API", swaggerConfig.springShopOpenAPI().getInfo().getTitle());
    }

    @Test
    @DisplayName("Doit retourner un objet openapi avec l'url d'une licence correcte")
    void springShopCorrectLicenseUrl() {
        assertEquals(
                "http://springdoc.org",
                swaggerConfig.springShopOpenAPI().getInfo().getLicense().getUrl());
    }

    @Test
    @DisplayName("Doit retourner un objet openapi avec une description correcte")
    void springShopCorrectDescription() {
        assertEquals(
                "Formation sample application",
                swaggerConfig.springShopOpenAPI().getInfo().getDescription());
    }

    @Test
    @DisplayName("Doit retourner un objet openapi avec le nom d'une licence correcte")
    void springShopCorrectLicenseName() {
        assertEquals(
                "Apache 2.0", swaggerConfig.springShopOpenAPI().getInfo().getLicense().getName());
    }

    @Test
    @DisplayName("Doit retourner un objet openapi avec une version correct")
    void springShopCorrectVersion() {
        assertEquals("1.0.0", swaggerConfig.springShopOpenAPI().getInfo().getVersion());
    }

    @Test
    @DisplayName("SDoit retourner  groupedopenapi")
    void retournerGroupedOpenApi() {
        assertNotNull(swaggerConfig.publicApi());
    }
}