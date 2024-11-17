package edu.itq.soa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Dao{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int getMinutesPaid(String transactionId) {
        String sql = "SELECT minutesPaid FROM payments_history WHERE ticketId = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, transactionId);
    }

    public String getStreamingService(String transactionId) {
        String sql = "SELECT streamingService FROM payments_history WHERE ticketId = ?";
        return jdbcTemplate.queryForObject(sql, String.class, transactionId);
    }

    public void updateExpireDate(String streamingService, String email, String expireDate) {
        String sql = "UPDATE service_accounts SET expireDate = ? WHERE email = ? AND streamingService = ?";
        jdbcTemplate.update(sql, expireDate, email, streamingService);
    }

    public double getTotalPaid(String transactionId) {
        String sql = "SELECT total FROM payments_history WHERE ticketId = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, transactionId);
    }
}
