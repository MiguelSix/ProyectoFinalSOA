package edu.itq.soa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Dao{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean login(String email, String password) {
        String sql = "SELECT * FROM service_accounts WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForList(sql, email, password).size() > 0;
    }

    public boolean serviceExists(String streamingService) {
        String sql = "SELECT streamingService FROM streaming_services WHERE streamingService = ?";
        return jdbcTemplate.queryForList(sql, streamingService).size() > 0;
    }

    public String getExpireDate(String email, String streamingService) {
        String sql = "SELECT expireDate FROM service_accounts WHERE email = ? AND streamingService = ?";
        return jdbcTemplate.queryForObject(sql, String.class, email, streamingService);
    }
    
}
