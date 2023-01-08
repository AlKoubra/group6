package com.ca.formation.formationdemo1.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private AuthenticationManagerBuilder auth;
    @InjectMocks
    private SecurityConfig securityConfig;


    @Test
    void passwordEncoderShouldReturnBCryptPasswordEncoder() {
        assertTrue(securityConfig.passwordEncoder() instanceof BCryptPasswordEncoder);
    }

    @Test
    void configureShouldAuthenticateAnyOtherRequest() throws Exception {
        securityConfig.configure(auth);
        verify(auth, times(1)).userDetailsService(any());
    }

}