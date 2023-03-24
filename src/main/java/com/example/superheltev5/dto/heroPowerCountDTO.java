package com.example.superheltev5.dto;

public class heroPowerCountDTO {
    String hName, rName;
    int numberOfPowers;

    public heroPowerCountDTO(String hName, String rName, int numberOfPowers) {
        this.hName = hName;
        this.rName = rName;
        this.numberOfPowers = numberOfPowers;
    }

    public String gethName() {
        return hName;
    }

    public String getrName() {
        return rName == null ? "Unknown" : rName;
    }

    public int getNumberOfPowers() {
        return numberOfPowers;
    }
}
