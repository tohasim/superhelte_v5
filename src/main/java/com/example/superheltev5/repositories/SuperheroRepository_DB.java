package com.example.superheltev5.repositories;

import com.example.superheltev5.dto.*;
import com.example.superheltev5.model.Superhero;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("SuperheroRepository_DB")
public class SuperheroRepository_DB implements IRepository {
    @Override
    public List<heroYearDTO> getSuperheroesWithYear() {
        List<heroYearDTO> heroes = new ArrayList<>();
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, rname, creationyear FROM SUPERHERO";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                heroes.add(new heroYearDTO(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return heroes;
    }

    @Override
    public List<heroPowerCountDTO> getSuperheroesWithNumberOfPowers() {
        List<heroPowerCountDTO> heroes = new ArrayList<>();
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, rname, count(*) FROM superhero INNER JOIN superhero_powers using (hero_id) INNER JOIN superpower USING (power_id) GROUP BY (hero_id);";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                heroes.add(new heroPowerCountDTO(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return heroes;
    }

    @Override
    public List<heroPowersDTO> getSuperheroesWithPowers() {
        boolean heroAlreadyPresent;
        List<heroPowersDTO> heroes = new ArrayList<>();
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, rname, pname FROM superhero INNER JOIN superhero_powers using (hero_id) INNER JOIN superpower USING (power_id)";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                heroAlreadyPresent = false;
                for (heroPowersDTO hero : heroes) {
                    if (hero.getHname().equals(rs.getString(1))){
                        hero.addPower(rs.getString(3));
                        heroAlreadyPresent = true;
                    }
                }
                if (!heroAlreadyPresent)
                    heroes.add(new heroPowersDTO(rs.getString(1), rs.getString(2), new ArrayList<>(List.of(rs.getString(3)))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return heroes;
    }

    @Override
    public List<heroCityDTO> getSuperheroesWithCity() {
        List<heroCityDTO> heroes = new ArrayList<>();
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, cname FROM SUPERHERO INNER JOIN CITY USING(CITY_ID);";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                heroes.add(new heroCityDTO(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return heroes;
    }

    @Override
    public heroYearDTO searchSuperheroWithYear(String name) {
        heroYearDTO hero = null;
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, rname, creationyear FROM SUPERHERO WHERE hname = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                hero = new heroYearDTO(rs.getString(1), rs.getString(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hero;
    }

    @Override
    public heroPowerCountDTO searchSuperheroWithNumberOfPowers(String name) {
        heroPowerCountDTO hero = null;
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, rname, count(*) FROM superhero INNER JOIN superhero_powers using (hero_id) INNER JOIN superpower USING (power_id) WHERE hname = ? GROUP BY (hero_id);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                hero = new heroPowerCountDTO(rs.getString(1), rs.getString(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hero;
    }

    @Override
    public heroPowersDTO searchSuperheroWithPowers(String name) {
        heroPowersDTO hero = null;
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, rname, pname FROM superhero INNER JOIN superhero_powers using (hero_id) INNER JOIN superpower USING (power_id) WHERE hname = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                hero = new heroPowersDTO(rs.getString(1), rs.getString(2), new ArrayList<>(List.of(rs.getString(3))));
                boolean shouldCheck = true;
                while(shouldCheck && rs.next()){
                    if (rs.getString(1).equals(hero.getHname())){
                        hero.addPower(rs.getString(3));
                    }
                    else shouldCheck = false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hero;
    }

    @Override
    public heroCityDTO searchSuperheroWithCity(String name) {
        heroCityDTO hero = null;
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, cname FROM SUPERHERO INNER JOIN CITY USING(CITY_ID) WHERE hname = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                hero = new heroCityDTO(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hero;
    }

    @Override
    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        try{
            Connection con = DBManager.getConnection();
            String query = "SELECT cname FROM CITY";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cities.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    public List<String> getPowers(){
        List<String> powers = new ArrayList<>();
        try{
            Connection con = DBManager.getConnection();
            String query = "SELECT pname FROM SUPERPOWER";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                powers.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return powers;
    }

    @Override
    public void postHero(Superhero hero) {
        try {
            Connection con = DBManager.getConnection();
            // ID's
            int cityId = 0;
            int heroId = 0;
            List<Integer> powerIDs = new ArrayList<>();

            // find city_id
            String SQL1 = "select city_id from city where cname = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, hero.getCity());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cityId = rs.getInt("city_id");
            }

            // insert row in superhero table
            String SQL2 = "insert into superhero (hname, rname, creationyear, city_id) " +
                    "values(?, ?, ?, ?);";
            pstmt = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS); // return autoincremented key
            pstmt.setString(1, hero.getSuperheroName());
            pstmt.setString(2, hero.getName());
            pstmt.setInt(3, hero.getCreationYear());
            pstmt.setInt(4, cityId);

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                heroId = rs.getInt(1);
            }

            // find power_ids
            String SQL3 = "select power_id from superpower where pname = ?;";
            pstmt = con.prepareStatement(SQL3);
            for (String power : hero.getSuperPowers()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("power_id"));
                }
            }

            // insert entries in superhero_powers join table
            String SQL4 = "insert into superhero_powers values (?,?);";
            pstmt = con.prepareStatement(SQL4);
            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, heroId);
                pstmt.setInt(2, powerIDs.get(i));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
