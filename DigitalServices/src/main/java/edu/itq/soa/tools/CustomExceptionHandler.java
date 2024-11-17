
package edu.itq.soa.tools;

import java.sql.SQLException;
import org.apache.activemq.artemis.api.core.ActiveMQNotConnectedException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;
import jakarta.jms.JMSException;

@Component
public class CustomExceptionHandler {

    public void handleException(Exception e) {
        System.err.println("Exception: " + e.getMessage());
    }

    public void handleDatabaseConnectionException(CannotGetJdbcConnectionException e) {
        System.err.println("Database Connection Exception: " + e.getMessage());
    }
    
    public void handleDuplicateKeyException(SQLException e) {
        System.err.println("Duplicate Key Exception: " + e.getMessage());
    }

    public void handleSQLException(SQLException e) {
        System.err.println("SQL Exception: " + e.getMessage());
    }
    
    public void handleArtemisException(ActiveMQNotConnectedException e) {
        System.err.println("Artemis Exception: " + e.getMessage());
    }
    
    public void handleJMSException(JMSException e) {
        System.err.println("JMS Exception: " + e.getMessage());
    }
    
    public void handleIllegalArgumentException(IllegalArgumentException e) {
        System.err.println("Illegal Argument Exception: " + e.getMessage());
    }
}
