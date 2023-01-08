package com.ca.formation.formationdemo1.config.jwtconfig;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Test

    void doFilterInternalLorsqueEnteteNull() {
        try {
            jwtFilter.doFilterInternal(request, response, filterChain);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test

    void doFilterInternalLorsqueEnteteNotNull() {
        String header = "Basic";

        try {
            jwtFilter.doFilterInternal(request, response, filterChain);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }


    @Test

    void doFilterInternalLorsqueTousLesConditionsSontRemplies() {
        String header =
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6IkxhY3JvaXgsSmVhbiIsImlzcyI6ImZvcm1hdGlvbi5jYSIsImlhdCI6MTU5MjQ4MjQ2NiwiZXhwIjoxNTkyNTM2MjY2fQ.q-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_X";
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6IkxhY3JvaXgsSmVhbiIsImlzcyI6ImZvcm1hdGlvbi5jYSIsImlhdCI6MTU5MjQ4MjQ2NiwiZXhwIjoxNTkyNTM2MjY2fQ.q-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_Xq-8_7-_X";

        try {
            jwtFilter.doFilterInternal(request, response, filterChain);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}