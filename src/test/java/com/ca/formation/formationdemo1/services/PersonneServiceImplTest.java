package com.ca.formation.formationdemo1.services;

import com.ca.formation.formationdemo1.exception.ResourceNotFoundException;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.models.Utilisateur;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;
import com.ca.formation.formationdemo1.repositories.UtilisateurRepository;
import com.ca.formation.formationdemo1.services.impl.PersonneServiceImpl;
import com.ca.formation.formationdemo1.services.impl.UtilisateurServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PersonneServiceImplTest {

  @Mock
  PersonneRepository personneRepository;

  @InjectMocks
  private PersonneServiceImpl personneServiceImpl;

  @Mock
  UtilisateurRepository utilisateurRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private AuthenticationManager authenticationManager;
  @Before("")
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }
  @InjectMocks
  private UtilisateurServiceImpl utilisateurServiceImpl;

  @Test
  public void ajouterPersonne() {
    Personne personne = new Personne("serignecheikh", "thioune", 50);
    personne.setId(1L);
    when(personneRepository.save(any())).thenReturn(personne);

    Personne personneResponse = personneServiceImpl.addPersonne(new Personne("tonux", "samb", 50));

    assertNotNull(personneResponse);

    verify(personneRepository, atLeastOnce()).save(any());
  }


  //Test de la m??thode getPersonne
  @Test
  public void getPersonne() throws ResourceNotFoundException {
    Personne personne = new Personne("tonux", "samb",50);
    when(personneRepository.findById(1L)).thenReturn(Optional.of(personne));

    Personne personneResponse = personneServiceImpl.getPersonne(1L);

    assertNotNull(personneResponse);
    assertEquals(personne.getNom(), personneResponse.getNom());
    assertEquals(personne.getPrenom(), personneResponse.getPrenom());
    assertEquals(personne.getAge(), personneResponse.getAge());
  }
  //Test de la m??thode updatepersone
  @Test
  public void updatePersonne() throws ResourceNotFoundException {
    Personne personne = new Personne("tonux", "samb", 50);
    personne.setId(1L);
    when(personneRepository.findById(anyLong())).thenReturn(Optional.of(personne));
    when(personneRepository.save(any())).thenReturn(personne);

    Personne personneResponse = personneServiceImpl.updatePersonne(1L, new Personne("tonux", "samb", 55));

    assertNotNull(personneResponse);
    assertEquals(1L, personneResponse.getId().longValue());
    assertEquals(55, personneResponse.getAge());

    verify(personneRepository, atLeastOnce()).findById(anyLong());
    verify(personneRepository, atLeastOnce()).save(any());
  }

  //Test de la m??thode getPersonnes

  @Test
  public void getAllPersonnes() {
    // Cr??ation de la liste de personnes attendue
    List<Personne> personnes = Arrays.asList(
            new Personne("tonux", "samb", 50),
            new Personne("thioune", "serignecheikh", 23),
            new Personne("niang", "ndeyekhady", 23),
            new Personne("sow", "fatou", 26),
            new Personne("seye", "madjiguen", 24)
    );

    // Configuration du comportement du mock du repository de personne
    when(personneRepository.findAll()).thenReturn(personnes);

// Appel de la m??thode ?? tester
    List<Personne> personnesResponse = personneServiceImpl.getPersonnes();

    // V??rification du r??sultat
    assertNotNull(personnesResponse);
    assertEquals(personnes, personnesResponse);
  }

  @Test
  public void getPersonneParNom() {
    // Cr??ation de la liste de personnes attendue
    List<Personne> personnes = Arrays.asList(
            new Personne("tonux", "samb", 50),
            new Personne("tonux", "sakho", 30)
    );

    // Configuration du comportement du mock du repository de personne
    when(personneRepository.findByNom("tonux")).thenReturn(personnes);

    // Appel de la m??thode ?? tester
    List<Personne> personnesResponse = personneServiceImpl.getPersonneParNom("tonux");

    // V??rification du r??sultat
    assertNotNull(personnesResponse);
    assertEquals(personnes, personnesResponse);
  }

  //Test de la m??thode deletePersonne
  @Test
  public void deletePersonne() {
    Personne personne = new Personne("tonux", "samb", 50);
    personne.setId(1L);
    when(personneRepository.findById(anyLong())).thenReturn(Optional.of(personne));

    personneServiceImpl.deletePersonne(1L);

    verify(personneRepository, atLeastOnce()).deleteById(1L);
  }

  // Test de la m??thode login

  @Test
  public void testLogin() {
    // Pr??parer les donn??es de test
    String username = "username";
    String password = "password";
    Utilisateur utilisateurRequest = new Utilisateur(username, password);

    // Mocker le comportement du repository
    Utilisateur utilisateurMock = new Utilisateur(username, password);
    when(utilisateurRepository.findByUsername(username)).thenReturn(Optional.of(utilisateurMock));

    // Mocker le comportement de l'authentication manager
    Authentication authenticationMock = mock(Authentication.class);
    when(authenticationManager.authenticate(any())).thenReturn(authenticationMock);
    when(authenticationMock.getPrincipal()).thenReturn(utilisateurMock);

    // Appel de la m??thode ?? tester
    Utilisateur utilisateurResponse = utilisateurServiceImpl.login(utilisateurRequest);

    // V??rifions que la m??thode retourne bien l'utilisateur attendu
    assertEquals(utilisateurMock, utilisateurResponse);
  }

  // Test de la m??thode registration
  @Test
  public void testRegistration() throws ValidationException {
    // Pr??parer les donn??es de test
    String username = "username";
    String password = "password";
    Utilisateur utilisateurRequest = new Utilisateur(username, password);

    // Mocker le comportement du repository
    Utilisateur utilisateurMock = new Utilisateur(username, password);
    when(utilisateurRepository.save(utilisateurRequest)).thenReturn(utilisateurMock);

    // Mocker le comportement du password encoder
    String encodedPassword = "encoded_password";
    when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

    // Appel de la m??thode ?? tester
    Utilisateur utilisateurResponse = utilisateurServiceImpl.registration(utilisateurRequest);

    // V??rifions que la m??thode retourne bien l'utilisateur attendu
    assertEquals(utilisateurMock, utilisateurResponse);
  }


}
