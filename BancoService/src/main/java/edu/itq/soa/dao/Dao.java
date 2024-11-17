package edu.itq.soa.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Dao{
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public double getServiceCost(String service) {
        String sql = "SELECT pricePerMinute FROM streaming_services WHERE streamingService = ?";
        double rs = jdbcTemplate.queryForObject(sql, Double.class, service);
        return rs;
    }
    
    public String getCardNumber(String email) {
        String sql = "SELECT cardNumber FROM service_accounts WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, String.class, email);
    }
    
    public double getUserBalance(String cardNumber) {
        String sql = "SELECT balance FROM bank_accounts WHERE cardNumber = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, cardNumber);
    }
    
    public void updateUserBalance(String cardNumber, double newBalance) {
        String sql = "UPDATE bank_accounts SET balance = ? WHERE cardNumber = ?";
        jdbcTemplate.update(sql, newBalance, cardNumber);
    }

    public void insertTransaction(String email, String streamingService, int timeRequested, double totalCost, String transactionId) {
        System.out.println(totalCost);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sql = "INSERT INTO payments_history (date, minutesPaid, total, email, streamingService, ticketId) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, date, timeRequested, totalCost, email, streamingService, transactionId);
    }
    
}
