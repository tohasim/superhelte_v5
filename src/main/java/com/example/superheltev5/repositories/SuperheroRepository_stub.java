package com.example.superheltev5.repositories;

import com.example.superheltev5.dto.heroCityDTO;
import com.example.superheltev5.dto.heroPowerCountDTO;
import com.example.superheltev5.dto.heroPowersDTO;
import com.example.superheltev5.dto.heroYearDTO;
import com.example.superheltev5.model.Superhero;

import java.util.List;

public class SuperheroRepository_stub implements IRepository{
    @Override
    public List<heroYearDTO> getSuperheroesWithYear() {
        return null;
    }

    @Override
    public List<heroPowerCountDTO> getSuperheroesWithNumberOfPowers() {
        return null;
    }

    @Override
    public List<heroPowersDTO> getSuperheroesWithPowers() {
        return null;
    }

    @Override
    public List<heroCityDTO> getSuperheroesWithCity() {
        return null;
    }

    @Override
    public heroYearDTO searchSuperheroWithYear(String name) {
        return null;
    }

    @Override
    public heroPowerCountDTO searchSuperheroWithNumberOfPowers(String name) {
        return null;
    }

    @Override
    public heroPowersDTO searchSuperheroWithPowers(String name) {
        return null;
    }

    @Override
    public heroCityDTO searchSuperheroWithCity(String name) {
        return null;
    }

    @Override
    public List<String> getCities() {
        return null;
    }

    @Override
    public List<String> getPowers() {
        return null;
    }

    @Override
    public void postHero(Superhero hero) {

    }
}
