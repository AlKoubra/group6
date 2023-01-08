package com.ca.formation.formationdemo1.repositories;

import com.ca.formation.formationdemo1.models.Personne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PersonneRepositoryTest {

    @Autowired
    PersonneRepository personneRepository;

    @Test
    public void ajouterPersonne() {
        Personne personne = personneRepository.save(new Personne("fatou", "SOW", 50));
        assertNotNull(personne);
        assertEquals(personne.getNom(), "fatou");
    }

    @Test
    public void update()
    {

        //Given
        Personne personne = personneRepository.save(new Personne("tonux", "samb", 50));
        personne.setNom("Fatou SOW");
        //When
        Personne personneUpdated = personneRepository.save(personne);
        //Then
        assertNotNull(personneUpdated);
        assertEquals("FatouS OW", personneUpdated.getNom());
    }

    @Test
    public void DeleteById() {
        Personne personne = personneRepository.save(new Personne("Fatou", "SOW", 50));
        personneRepository.delete(personne);
        assertNotNull(personneRepository.findById(personne.getId()));
    }

    @Test

    public void findAll() {
        Personne personne = personneRepository.save(new Personne("Fatou", "SOW", 50));
        personneRepository.findAll();
        assertNotNull(personneRepository.findAll());

    }
    @Test
    public void findByNom()
    {
        Personne personne = personneRepository.save(new Personne("Fatou", "SOW", 50));
        List <Personne> p= personneRepository.findByNom(personne.getNom());
        assertNotNull(p);
    }
    @Test
    public  void findByNomAndPrenom()
    {
        Personne personne = personneRepository.save(new Personne("fatou", "SOW", 50));
        List<Personne> p=personneRepository.findByNomAndPrenom(personne.getNom(),personne.getPrenom());
        assertNotNull(p);
    }
    @Test
    public  void ageGreaterThan()
    {
        Personne personne = personneRepository.save(new Personne("fatou", "SOW", 50));
        List<Personne> p=personneRepository.ageGreaterThan(personne.getAge());
        assertNotNull(p);
    }

}