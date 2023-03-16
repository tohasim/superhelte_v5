package com.example.superheltev5.repositories;

import com.example.superheltev5.dto.*;
import com.example.superheltev5.model.Superhero;

import java.util.List;

public interface IRepository {
    List<heroYearDTO> getSuperheroesWithYear();
    List<heroPowerCountDTO> getSuperheroesWithNumberOfPowers();
    List<heroPowersDTO> getSuperheroesWithPowers();
    List<heroCityDTO> getSuperheroesWithCity();

    heroYearDTO searchSuperheroWithYear(String name);
    heroPowerCountDTO searchSuperheroWithNumberOfPowers(String name);
    heroPowersDTO searchSuperheroWithPowers(String name);
    heroCityDTO searchSuperheroWithCity(String name);

    List<String> getCities();

    List<String> getPowers();

    void postHero(Superhero hero);
}
