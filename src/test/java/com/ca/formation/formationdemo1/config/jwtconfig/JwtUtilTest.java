package com.ca.formation.formationdemo1.config.jwtconfig;

import com.ca.formation.formationdemo1.models.Utilisateur;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void getClaimsWhenTokenIsValid() {
        Utilisateur utilisateur = new Utilisateur("admin", "admin", "admin", new HashSet<>());
        String token = jwtUtil.generateAccesToken(utilisateur);
        assertNotNull(jwtUtil.getClaims(token));
    }

    @Test
    void getClaimsWhenTokenIsInvalid() {
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6ImZvcm1hdGlvbi5jYSIsImlhdCI6MTU4NjQwMzY2NiwiZXhwIjoxNTg2NDkxMDY2fQ.X-_q-7_8-9_0-a_b-c_d-e_f-g_h-i_j-k_l-m_n-o_p-q_r-s_t-u_v-w_x-y_z";
        assertThrows(SignatureException.class, () -> jwtUtil.getClaims(token));
    }

    @Test
    void refreshAccesToken() {
        Utilisateur utilisateur = new Utilisateur("admin", "admin", "admin", new HashSet<>());
        String token = jwtUtil.refreshAccesToken(utilisateur);
        assertNotNull(token);
        assertTrue(jwtUtil.validate(token));
    }

    @Test
    void generateAcces() {
        Utilisateur utilisateur = new Utilisateur("admin", "admin", "admin", new HashSet<>());
        String token = jwtUtil.generateAccesToken(utilisateur);
        assertNotNull(token);
    }

    @Test
    void getUsername() {
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTg2MjYwNjE5LCJleHAiOjE1ODYzNDcwMTl9.q-_7-8Q6_3-XqyvZK8k5Y3x7m6LH4n_2QrZKvRqXsQt0uWYy7D5k6-9g3BHp8l2e_4nPfTmCxrLcRtXoZKPWcA";
        assertThrows(SignatureException.class, () -> jwtUtil.getUsername(token));
    }

    @Test
    void validateWhenTokenIsExpired() {
        String token =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImZvcm1hdGlvbi5jYSIsImlhdCI6MTU4NjY0MjQwMCwiZXhwIjoxNTg2NjQ2MDAwfQ.X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3_X-_q-8_7-3";
        assertFalse(jwtUtil.validate(token));
    }
}