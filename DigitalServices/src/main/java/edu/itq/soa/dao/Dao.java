package edu.itq.soa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Dao{
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public List<String> getServices() {
        String sql = "SELECT streamingService FROM streaming_services";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<String> services = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            services.add((String) row.get("streamingService"));
        }
        return services;
    }
    
    @SuppressWarnings("deprecation")
    public boolean isRegistered(String email, String streamingService) {
        String sql = "SELECT COUNT(*) FROM service_accounts WHERE email = ? AND streamingService = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { email, streamingService }, Integer.class) > 0;
    }
    
}
