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
    public List<HeroYearDTO> getSuperheroesWithYear() {
        List<HeroYearDTO> heroes = new ArrayList<>();
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hero_id, hname, rname, creationyear FROM SUPERHERO";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                heroes.add(new HeroYearDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
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
            String query = "SELECT hname, rname, count(*) FROM SUPERHERO INNER JOIN SUPERHERO_POWERS using (hero_id) INNER JOIN SUPERPOWER USING (power_id) GROUP BY (hero_id);";
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
    public List<HeroPowersDTO> getSuperheroesWithPowers() {
        boolean heroAlreadyPresent;
        List<HeroPowersDTO> heroes = new ArrayList<>();
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hero_id, hname, rname, pname FROM SUPERHERO INNER JOIN SUPERHERO_POWERS using (hero_id) INNER JOIN SUPERPOWER USING (power_id)";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                heroAlreadyPresent = false;
                for (HeroPowersDTO hero : heroes) {
                    if (hero.getHname().equals(rs.getString(2))){
                        hero.addPower(rs.getString(4));
                        heroAlreadyPresent = true;
                    }
                }
                if (!heroAlreadyPresent)
                    heroes.add(new HeroPowersDTO(rs.getInt(1), rs.getString(2), rs.getString(3), new ArrayList<>(List.of(rs.getString(4)))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return heroes;
    }

    @Override
    public List<HeroCityDTO> getSuperheroesWithCity() {
        List<HeroCityDTO> heroes = new ArrayList<>();
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, cname FROM SUPERHERO INNER JOIN CITY USING(CITY_ID);";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                heroes.add(new HeroCityDTO(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return heroes;
    }

    @Override
    public HeroYearDTO searchSuperheroWithYear(int id) {
        HeroYearDTO hero = null;
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hero_id, hname, rname, creationyear FROM SUPERHERO WHERE hero_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                hero = new HeroYearDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hero;
    }

    @Override
    public heroPowerCountDTO searchSuperheroWithNumberOfPowers(int id) {
        heroPowerCountDTO hero = null;
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, rname, count(*) FROM SUPERHERO INNER JOIN SUPERHERO_POWERS using (hero_id) INNER JOIN SUPERPOWER USING (power_id) WHERE hero_id = ? GROUP BY (hero_id);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
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
    public HeroPowersDTO searchSuperheroWithPowers(int id) {
        HeroPowersDTO hero = null;
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hero_id, hname, rname, pname FROM SUPERHERO INNER JOIN SUPERHERO_POWERS using (hero_id) INNER JOIN SUPERPOWER USING (power_id) WHERE hero_id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                hero = new HeroPowersDTO(rs.getInt(1), rs.getString(2), rs.getString(3), new ArrayList<>(List.of(rs.getString(4))));
                boolean shouldCheck = true;
                while(shouldCheck && rs.next()){
                    if (rs.getString(2).equals(hero.getHname())){
                        hero.addPower(rs.getString(4));
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
    public HeroCityDTO searchSuperheroWithCity(int id) {
        HeroCityDTO hero = null;
        try {Connection con = DBManager.getConnection();
            String query = "SELECT hname, cname FROM SUPERHERO INNER JOIN CITY USING(CITY_ID) WHERE hero_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                hero = new HeroCityDTO(rs.getString(1), rs.getString(2));
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
            String SQL1 = "select city_id from CITY where cname = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, hero.getCity());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cityId = rs.getInt("city_id");
            }

            // insert row in superhero table
            String SQL2 = "insert into SUPERHERO (hname, rname, creationyear, city_id) " +
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
            String SQL3 = "select power_id from SUPERPOWER where pname = ?;";
            pstmt = con.prepareStatement(SQL3);
            for (String power : hero.getSuperPowers()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("power_id"));
                }
            }

            // insert entries in superhero_powers join table
            String SQL4 = "insert into SUPERHERO_POWERS values (?,?);";
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

    //
    @Override
    public void updateHero(int id, Superhero hero) {
        try {
            int cityID = 0;
            int powerID = 0;

            Connection con = DBManager.getConnection();

            String query1 = "select city_id from CITY where cname = ?;";
            PreparedStatement pstmt = con.prepareStatement(query1);
            pstmt.setString(1, hero.getCity());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cityID = rs.getInt("city_id");
            }


            int i = 0;
            String query = "UPDATE SUPERHERO SET hname = ?, rname = ?, creationyear = ?, city_id = ? WHERE hero_id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, hero.getSuperheroName());
            pstmt.setString(2, hero.getName());
            pstmt.setInt(3, hero.getCreationYear());
            pstmt.setInt(4, cityID);
            pstmt.setInt(5, id);
            System.out.println("FROM UPDATE HERO: " + hero);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Superhero getSuperHero(int id) {
        HeroPowersDTO heroPowersDTO = searchSuperheroWithPowers(id);
        HeroCityDTO heroCityDTO = searchSuperheroWithCity(id);
        HeroYearDTO heroYearDTO = searchSuperheroWithYear(id);

        int heroID = id;
        String rname = heroPowersDTO.getRname();
        String city = heroCityDTO.getCity();
        String superheroName = heroYearDTO.gethName();
        List<String> powers = heroPowersDTO.getPowers();
        int year = heroYearDTO.getCreationYear();

        return new Superhero(heroID, rname, city, superheroName, powers, year);
    }

}
