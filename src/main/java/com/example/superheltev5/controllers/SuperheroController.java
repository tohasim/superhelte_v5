package com.example.superheltev5.controllers;


import com.example.superheltev5.dto.*;
import com.example.superheltev5.model.Superhero;
import com.example.superheltev5.repositories.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("superheroes")
public class SuperheroController {

    private IRepository superHeroRepository;

    public SuperheroController(ApplicationContext context, @Value("${superhero.repository.impl}") String impl) {
        this.superHeroRepository = (IRepository) context.getBean(impl);
    }

    @GetMapping("/")
    public String getSuperheroes(Model model){
        List<HeroYearDTO> superheroList = superHeroRepository.getSuperheroesWithYear();
        model.addAttribute("superheroList", superheroList);
        return "index";
    }

    @GetMapping("/superpowers/count")
    public ResponseEntity<?> getSuperheroesWithPowerCount(@RequestParam(required = false) String format){
        List<heroPowerCountDTO> superheroList = superHeroRepository.getSuperheroesWithNumberOfPowers();
        //hname, rname, powercount
        if (format != null && format.equals("html")){
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("<h1> %s </h1>", "SUPERHELTE:"));
            for (heroPowerCountDTO superhero : superheroList) {
                sb.append(String.format("<h2> %s:</h2>", superhero.gethName()));
                sb.append("<ul>");
                sb.append(String.format("<li> Ægte navn: %s </li>", superhero.getrName()));
                sb.append(String.format("<li> Antal kræfter: %d </li>", superhero.getNumberOfPowers()));
                sb.append("</ul>");
            }
            HttpHeaders header = new HttpHeaders();
            header.set("Superhero header", "content-type:text/html");
            return new ResponseEntity<>(sb.toString(), header, HttpStatus.OK);
        }
        return new ResponseEntity<>(superheroList, HttpStatus.OK);
    }

    @GetMapping("/superpowers")
    public ResponseEntity<?> getSuperheroesWithPowers(@RequestParam(required = false) String format){
        List<HeroPowersDTO> superheroList = superHeroRepository.getSuperheroesWithPowers();
        //hname, rname, powers
        if (format != null && format.equals("html")){
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("<h1> %s </h1>", "SUPERHELTE:"));
            for (HeroPowersDTO superhero : superheroList) {
                sb.append(String.format("<h2> %s:</h2>", superhero.getHname()));
                sb.append("<ul>");
                sb.append(String.format("<li> Ægte navn: %s </li>", superhero.getRname()));
                sb.append(String.format("<li> Kræfter: %s </li>", superhero.getPowers()));
                sb.append("</ul>");
            }
            HttpHeaders header = new HttpHeaders();
            header.set("Superhero header", "content-type:text/html");
            return new ResponseEntity<>(sb.toString(), header, HttpStatus.OK);
        }
        return new ResponseEntity<>(superheroList, HttpStatus.OK);
    }

    @GetMapping("/city")
    public ResponseEntity<?> getSuperheroesWithCity(@RequestParam(required = false) String format){
        List<HeroCityDTO> superheroList = superHeroRepository.getSuperheroesWithCity();
        //hname, city
        if (format != null && format.equals("html")){
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("<h1> %s </h1>", "SUPERHELTE:"));
            for (HeroCityDTO superhero : superheroList) {
                sb.append(String.format("<h2> %s:</h2>", superhero.getHname()));
                sb.append("<ul>");
                sb.append(String.format("<li> By: %s </li>", superhero.getCity()));
                sb.append("</ul>");
            }
            HttpHeaders header = new HttpHeaders();
            header.set("Superhero header", "content-type:text/html");
            return new ResponseEntity<>(sb.toString(), header, HttpStatus.OK);
        }
        return new ResponseEntity<>(superheroList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSuperhero(@RequestParam(required = false) String format, @PathVariable int id){
        //hname, rname, creationyear
        HeroYearDTO superhero = superHeroRepository.searchSuperheroWithYear(id);
        if (format != null && format.equals("html")) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("<h2> %s:</h2>", superhero.gethName()));
            sb.append("<ul>");
            sb.append(String.format("<li> Ægte navn: %s </li>", superhero.getrName()));
            sb.append(String.format("<li> Lavet i år: %d </li>", superhero.getCreationYear()));
            sb.append("</ul>");
            HttpHeaders header = new HttpHeaders();
            header.set("Superhero header", "content-type:text/html");
            return new ResponseEntity<>(sb.toString(), header, HttpStatus.OK);
        }
        return new ResponseEntity<>(superhero, HttpStatus.OK);
    }

    @GetMapping("/superpowers/count/{id}")
    public ResponseEntity<?> getSuperheroWithPowerCount(@RequestParam(required = false) String format, @PathVariable int id){
        //hname, rname, powercount
        heroPowerCountDTO superhero = superHeroRepository.searchSuperheroWithNumberOfPowers(id);
        if (format != null && format.equals("html")) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("<h2> %s:</h2>", superhero.gethName()));
            sb.append("<ul>");
            sb.append(String.format("<li> Ægte navn: %s </li>", superhero.getrName()));
            sb.append(String.format("<li> Antal kræfter: %d </li>", superhero.getNumberOfPowers()));
            sb.append("</ul>");
            HttpHeaders header = new HttpHeaders();
            header.set("Superhero header", "content-type:text/html");
            return new ResponseEntity<>(sb.toString(), header, HttpStatus.OK);
        }
        return new ResponseEntity<>(superhero, HttpStatus.OK);
    }

    @GetMapping("/superpowers/{id}")
    public String getSuperheroWithPowers(Model model, @PathVariable int id){
        //hname, rname, powers
        HeroPowersDTO superhero = superHeroRepository.searchSuperheroWithPowers(id);
        model.addAttribute("hero", superhero);
        return "heroSuperpowers";
    }

    @GetMapping("/add")
    public String showForm(Model model){
        Superhero hero = new Superhero();
        model.addAttribute("hero", hero);
        List<String> cities = superHeroRepository.getCities();
        List<String> powers = superHeroRepository.getPowers();
        model.addAttribute("cities", cities);
        model.addAttribute("powers", powers);
        return "addHeroForm";
    }

    @PostMapping("/add")
    public String submitForm(@ModelAttribute("hero") Superhero hero){
        System.out.println(hero);
        superHeroRepository.postHero(hero);
        return "addHeroForm_success";
    }

    @GetMapping("/edit/{id}")
    public String editForm(Model model, @PathVariable int id){
        Superhero hero = superHeroRepository.getSuperHero(id);
        model.addAttribute("hero", hero);
        List<String> cities = superHeroRepository.getCities();
        List<String> powers = superHeroRepository.getPowers();
        model.addAttribute("cities", cities);
        model.addAttribute("powers", powers);
        return "editHeroForm";
    }

    @PostMapping("/edit/{id}")
    public String editForm(@ModelAttribute("hero") Superhero hero, @PathVariable int id){
        System.out.println(hero);
        superHeroRepository.updateHero(id, hero);
        return "editHeroForm_success";
    }
    @GetMapping("/city/{name}")
    public ResponseEntity<?> getSuperheroWithCity(@RequestParam(required = false) String format, @PathVariable int id){
        //hname, city
        HeroCityDTO superhero = superHeroRepository.searchSuperheroWithCity(id);
        if (format != null && format.equals("html")) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("<h2> %s:</h2>", superhero.getHname()));
            sb.append("<ul>");
            sb.append(String.format("<li> By: %s </li>", superhero.getCity()));
            sb.append("</ul>");
            HttpHeaders header = new HttpHeaders();
            header.set("Superhero header", "content-type:text/html");
            return new ResponseEntity<>(sb.toString(), header, HttpStatus.OK);
        }
        return new ResponseEntity<>(superHeroRepository.searchSuperheroWithCity(id), HttpStatus.OK);
    }
}