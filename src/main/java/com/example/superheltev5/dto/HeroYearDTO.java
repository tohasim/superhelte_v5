package com.example.superheltev5.dto;

public class HeroYearDTO {
    String hName, rName;
    int creationYear, heroID;

    public HeroYearDTO(int heroID, String hName, String rName, int creationYear) {
        this.hName = hName;
        this.rName = rName;
        this.creationYear = creationYear;
        this.heroID = heroID;
    }

    public String gethName() {
        return hName;
    }

    public String getrName() {
        return rName == null ? "Unknown" : rName;
    }

    public int getCreationYear() {
        return creationYear;
    }

    public int getHeroID() {
        return heroID;
    }
}
