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
    void springShopCorrectTitle() {
        assertEquals("Formtion API", swaggerConfig.springShopOpenAPI().getInfo().getTitle());
    }

    @Test

    void springShopListener() {
        assertEquals(
                "http://springdoc.org",
                swaggerConfig.springShopOpenAPI().getInfo().getLicense().getUrl());
    }

    @Test

    void springShopCorrectDescription() {
        assertEquals(
                "Formation sample application",
                swaggerConfig.springShopOpenAPI().getInfo().getDescription());
    }

    @Test

    void springShopCorrectLicenseName() {
        assertEquals(
                "Apache 2.0", swaggerConfig.springShopOpenAPI().getInfo().getLicense().getName());
    }

    @Test

    void springShopCorrectVersion() {
        assertEquals("1.0.0", swaggerConfig.springShopOpenAPI().getInfo().getVersion());
    }

    @Test

    void retournerGroupedOpenApi() {
        assertNotNull(swaggerConfig.publicApi());
    }
}