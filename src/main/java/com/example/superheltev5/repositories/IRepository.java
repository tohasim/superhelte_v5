package com.example.superheltev5.repositories;

import com.example.superheltev5.dto.*;
import com.example.superheltev5.model.Superhero;

import java.util.List;

public interface IRepository {
    List<HeroYearDTO> getSuperheroesWithYear();
    List<heroPowerCountDTO> getSuperheroesWithNumberOfPowers();
    List<HeroPowersDTO> getSuperheroesWithPowers();
    List<HeroCityDTO> getSuperheroesWithCity();

    HeroYearDTO searchSuperheroWithYear(int id);
    heroPowerCountDTO searchSuperheroWithNumberOfPowers(int id);
    HeroPowersDTO searchSuperheroWithPowers(int id);
    HeroCityDTO searchSuperheroWithCity(int id);

    List<String> getCities();

    List<String> getPowers();

    void postHero(Superhero hero);
    Superhero getSuperHero(int id);

    void updateHero(int id, Superhero hero);
}
