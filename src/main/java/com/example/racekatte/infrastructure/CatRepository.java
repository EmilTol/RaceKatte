package com.example.racekatte.infrastructure;
import com.example.racekatte.entity.Cat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CatRepository {

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


}