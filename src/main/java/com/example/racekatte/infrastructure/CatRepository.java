package com.example.racekatte.infrastructure;
import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.Race;
import com.example.racekatte.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CatRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public List<Cat> findByUserId(int userId) {
//        String sql = "SELECT id, userId, name, age, gender, description, img FROM cat WHERE userId = ?";
//        return jdbcTemplate.query(sql, new Object[]{userId},(rs, rowNum) -> {
//            Cat cat = new Cat();
//            cat.setId(rs.getInt("id"));
//            cat.setUserId(rs.getInt("id"));
//
//
//        }
//    }

    public List<Cat> findAllCatsAndUsers() {
        String sql = "SELECT \n" +
                "  c.id AS catId,\n" +
                "  c.name AS catName,\n" +
                "  c.age,\n" +
                "  c.gender,\n" +
                "  c.description,\n" +
                "  c.img,\n" +
                "\n" +
                "  u.id AS userId,\n" +
                "  u.email,\n" +
                "  u.firstName,\n" +
                "  u.lastName,\n" +
                "  u.postalCode,\n" +
                "  u.phoneNumber,\n" +
                "\n" +
                "  r.id AS raceId,\n" +
                "  r.name AS raceName\n" +
                "\n" +
                "FROM Cat c\n" +
                "JOIN User u ON c.userId = u.id\n" +
                "LEFT JOIN CatRace cr ON c.id = cr.catId\n" +
                "LEFT JOIN Race r ON cr.raceId = r.id;";

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

                int raceId = rs.getInt("raceId");
                if (!rs.wasNull()) {
                    Race race = new Race();
                    race.setId(raceId);
                    race.setName(rs.getString("raceName"));
                    cat.setRace(race);
                }

                cats.add(cat);
            }

            return cats;
        });

    }
}