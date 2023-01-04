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
    Personne personne = new Personne("tonux", "samb", 50);
    personne.setId(1L);
    when(personneRepository.save(any())).thenReturn(personne);

    Personne personneResponse = personneServiceImpl.addPersonne(new Personne("tonux", "samb", 50));

    assertNotNull(personneResponse);

    verify(personneRepository, atLeastOnce()).save(any());
  }


  //Test de la méthode getPersonne
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
  //Test de la méthode updatepersone
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

  //Test de la méthode getPersonnes

  @Test
  public void getAllPersonnes() {
    // Création de la liste de personnes attendue
    List<Personne> personnes = Arrays.asList(
            new Personne("tonux", "samb", 50),
            new Personne("thioune", "serignecheikh", 23),
            new Personne("niang", "ndeyekhady", 23),
            new Personne("sow", "fatou", 26),
            new Personne("seye", "madjiguen", 24)
    );

    // Configuration du comportement du mock du repository de personne
    when(personneRepository.findAll()).thenReturn(personnes);

// Appel de la méthode à tester
    List<Personne> personnesResponse = personneServiceImpl.getPersonnes();

    // Vérification du résultat
    assertNotNull(personnesResponse);
    assertEquals(personnes, personnesResponse);
  }

  @Test
  public void getPersonneParNom() {
    // Création de la liste de personnes attendue
    List<Personne> personnes = Arrays.asList(
            new Personne("tonux", "samb", 50),
            new Personne("tonux", "sakho", 30)
    );

    // Configuration du comportement du mock du repository de personne
    when(personneRepository.findByNom("tonux")).thenReturn(personnes);

    // Appel de la méthode à tester
    List<Personne> personnesResponse = personneServiceImpl.getPersonneParNom("tonux");

    // Vérification du résultat
    assertNotNull(personnesResponse);
    assertEquals(personnes, personnesResponse);
  }

  //Test de la méthode deletePersonne
  @Test
  public void deletePersonne() {
    Personne personne = new Personne("tonux", "samb", 50);
    personne.setId(1L);
    when(personneRepository.findById(anyLong())).thenReturn(Optional.of(personne));

    personneServiceImpl.deletePersonne(1L);

    verify(personneRepository, atLeastOnce()).deleteById(1L);
  }

  // Test de la méthode login

  @Test
  public void testLogin() {
    // Préparer les données de test
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

    // Appeler la méthode à tester
    Utilisateur utilisateurResponse = utilisateurServiceImpl.login(utilisateurRequest);

    // Vérifier que la méthode retourne bien l'utilisateur attendu
    assertEquals(utilisateurMock, utilisateurResponse);
  }

  // Test de la méthode registration
  @Test
  public void testRegistration() throws ValidationException {
    // Préparer les données de test
    String username = "username";
    String password = "password";
    Utilisateur utilisateurRequest = new Utilisateur(username, password);

    // Mocker le comportement du repository
    Utilisateur utilisateurMock = new Utilisateur(username, password);
    when(utilisateurRepository.save(utilisateurRequest)).thenReturn(utilisateurMock);

    // Mocker le comportement du password encoder
    String encodedPassword = "encoded_password";
    when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

    // Appeler la méthode à tester
    Utilisateur utilisateurResponse = utilisateurServiceImpl.registration(utilisateurRequest);

    // Vérifier que la méthode retourne bien l'utilisateur attendu
    assertEquals(utilisateurMock, utilisateurResponse);
  }


}
