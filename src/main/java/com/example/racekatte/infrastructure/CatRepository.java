package com.example.racekatte.infrastructure;

import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.Race;
import com.example.racekatte.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CatRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Cat> findCatsByUserId(int userId) {
        String sql = """
        SELECT 
            c.id AS catId,
            c.name AS catName,
            c.age,
            c.gender,
            c.description,
            c.img,
            c.raceId,
            c.userid,

            u.id AS userId,
            u.email,
            u.firstName,
            u.lastName,
            u.postalCode,
            u.phoneNumber,

            r.id AS raceId,
            r.name AS raceName

        FROM Cat c
        JOIN User u ON c.userId = u.id
        LEFT JOIN Race r ON c.raceId = r.id
        WHERE c.userId = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
            List<Cat> cats = new ArrayList<>();
            while (rs.next()) {
                Cat cat = new Cat();
                cat.setId(rs.getInt("catId"));
                cat.setName(rs.getString("catName"));
                cat.setAge(rs.getInt("age"));
                cat.setGender(rs.getString("gender"));
                cat.setDescription(rs.getString("description"));
                cat.setImg(rs.getString("img"));

                User user = new User();
                user.setId(rs.getInt("userId"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPostalCode(rs.getString("postalCode"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                cat.setUser(user);


                Race race = new Race();
                race.setId(rs.getInt("raceId"));
                race.setName(rs.getString("raceName"));
                cat.setRace(race);

                cats.add(cat);
            }
            return cats;
        });
    }

    public List<Cat> findAllCatsAndUsers() {
        String sql = "SELECT \n" +
                "  c.id AS catId,\n" +
                "  c.userId,\n" +
                "  c.name AS catName,\n" +
                "  c.age,\n" +
                "  c.gender,\n" +
                "  c.description,\n" +
                "  c.img,\n" +
                "  c.raceId,\n" +

                "  u.id AS userId,\n" +
                "  u.email,\n" +
                "  u.firstName,\n" +
                "  u.lastName,\n" +
                "  u.postalCode,\n" +
                "  u.phoneNumber,\n" +

                "  r.id AS raceId,\n" +
                "  r.name AS raceName\n" +
                "FROM Cat c\n" +
                "JOIN User u ON c.userId = u.id\n" +
                "LEFT JOIN Race r ON c.raceId = r.id;";

        return jdbcTemplate.query(sql, rs -> {
            List<Cat> cats = new ArrayList<>();
            while (rs.next()) {
                Cat cat = new Cat();
                cat.setId(rs.getInt("catId"));
                cat.setName(rs.getString("catName"));
                cat.setAge(rs.getInt("age"));
                cat.setGender(rs.getString("gender"));
                cat.setDescription(rs.getString("description"));
                cat.setImg(rs.getString("img"));

                User user = new User();
                user.setId(rs.getInt("userId"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPostalCode(rs.getString("postalCode"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                cat.setUser(user);


                    Race race = new Race();
                    race.setId(rs.getInt("raceId"));
                    race.setName(rs.getString("raceName"));
                    cat.setRace(race);

                cats.add(cat);
            }
            return cats;
        });
    }

    public List<Race> getAllRaces() {
        String sql = "SELECT id, name FROM Race";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Race race = new Race();
            race.setId(rs.getInt("id"));
            race.setName(rs.getString("name"));
            return race;
        });
    }

    public boolean registerCat(Cat cat) {
        String sql = "INSERT INTO Cat (userId, raceId, name, age, gender, description, img) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql,
                cat.getUserId(),
                cat.getRace() != null ? cat.getRace().getId() : null, // Resultat af at intet fucking virker
                cat.getName(),
                cat.getAge(),
                cat.getGender(),
                cat.getDescription(),
                cat.getImg()
        );
        return result == 1;
    }


    public void deleteCatById(int id) {
        String sql = "DELETE FROM Cat WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }


}
