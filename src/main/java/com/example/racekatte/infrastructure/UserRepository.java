package com.example.racekatte.infrastructure;

import com.example.racekatte.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByEmail(String email) {
        try {
            String sql = "SELECT id, email, password, firstName, lastName, postalCode, phoneNumber FROM User WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPostalCode(rs.getString("postalCode"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                return user;
            });
        } catch (EmptyResultDataAccessException e) { // Fanger hvis der ikke er noget resultat med den email
            return null;
        }
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO User (email, password, firstName, lastName, postalCode, phoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql,
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPostalCode(),
                user.getPhoneNumber());
        return result == 1;
    }
    public void updateUser(User user) {
        String sql = "UPDATE User SET email = ?,password = ?,firstName = ?,lastName = ?,postalCode = ?,phoneNumber = ? " +
                "WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql,
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPostalCode(),
                user.getPhoneNumber(),
                user.getId());

        if (updatedRows == 0) {
            System.out.println("Ingen rækker blev opdateret! ID: " + user.getId());
            }
    }

    public User findById(int id) {
        try {
            String sql = "SELECT * FROM User WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPostalCode(rs.getString("postalCode"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public void deleteById(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
