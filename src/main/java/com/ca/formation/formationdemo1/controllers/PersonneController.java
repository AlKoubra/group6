package com.ca.formation.formationdemo1.controllers;

import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonneController {

    private final PersonneRepository repository;

     PersonneController(PersonneRepository repository) {
        this.repository = repository;
    }

    @GetMapping
     String getPersonnes(Model model){
        model.addAttribute("personnes", repository.findAll());
        return "index";
    }

    @GetMapping("/nouveau")
    String nouveauPersonne(Personne personne){
        return "nouveau";
    }

    @PostMapping("/ajouterPersonne")
    String ajouterPersonne(Personne personne, Model model){
        repository.save(personne);
        return "redirect:/";

    }



}
