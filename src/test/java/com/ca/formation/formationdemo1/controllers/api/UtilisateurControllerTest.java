package com.ca.formation.formationdemo1.controllers.api;

import com.ca.formation.formationdemo1.config.jwtconfig.JwtUtil;
import com.ca.formation.formationdemo1.models.Utilisateur;
import com.ca.formation.formationdemo1.services.UtilisateurService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UtilisateurControllerTest {

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UtilisateurController utilisateurController;



    @Test
    @DisplayName("Should return a status code of 200 when the user is created")
    void registrationWhenUserIsCreatedThenReturnStatusCode200() throws javax.xml.bind.ValidationException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername("test");
        utilisateur.setPassword("test");

       // when(utilisateurService.    registration(any(Utilisateur.class))).thenReturn(utilisateur);

        ResponseEntity<Utilisateur> responseEntity =
                utilisateurController.registration(utilisateur);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    @DisplayName("Should return 401 when the credentials are incorrect")
    void loginWhenCredentialsAreIncorrectThenReturn401() {
        Utilisateur utilisateurRequest = new Utilisateur("admin", "admin");
        when(utilisateurService.login(any(Utilisateur.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<Utilisateur> responseEntity =
                utilisateurController.login(utilisateurRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should return 200 when the credentials are correct")
    void loginWhenCredentialsAreCorrectThenReturn200() {
        Utilisateur utilisateur = new Utilisateur("admin", "admin");
        when(utilisateurService.login(any(Utilisateur.class))).thenReturn(utilisateur);
        when(jwtUtil.generateAccesToken(any(Utilisateur.class))).thenReturn("token");
        when(jwtUtil.refreshAccesToken(any(Utilisateur.class))).thenReturn("refresh_token");


        ResponseEntity<Utilisateur> responseEntity = utilisateurController.login(utilisateur);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().get(HttpHeaders.AUTHORIZATION));
        assertNotNull(responseEntity.getHeaders().get("refresh_token"));
    }


}
