package edu.itq.soa.business;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import edu.itq.soa.dao.Dao;
import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.Response;
import edu.itq.soa.jms.JmsSender;
import edu.itq.soa.tools.CustomExceptionHandler;

@Service
public class BusinessLogic {

    @Autowired
    private Dao dao;
    
    @Autowired
    private JmsSender jmsSender;
    
    @Autowired
    private CustomExceptionHandler customExceptionHandler;
    
    public void execute(JmsMessage jmsMessage) throws SQLException {
        Gson gson = new Gson();
        Request request = gson.fromJson(jmsMessage.getMessage(), Request.class);        
        Response response = new Response();
    
        try {
            
            double totalCost = calculateServiceCost(request.getStreamingService(), request.getTimeRequested());
            String cardNumber = dao.getCardNumber(request.getEmail());
            double userBalance = dao.getUserBalance(cardNumber);
            
            if (userBalance >= totalCost) {
                userBalance -= totalCost;
                dao.updateUserBalance(cardNumber, userBalance);
                String transactionId = "" + System.currentTimeMillis();
                dao.insertTransaction(
                        request.getEmail(), 
                        request.getStreamingService(), 
                        request.getTimeRequested(), 
                        totalCost,
                        transactionId);
                
                response.setStatus("ACEPTADA");
                response.setEmail(request.getEmail());
                response.setMsg("Pago exitoso");
                response.setTransactionId(transactionId);
                
            } else {
                response.setStatus("RECHAZADA");
                response.setEmail(request.getEmail());
                response.setMsg("Saldo insuficiente");
                response.setTransactionId("NULL");
            }
            
            jmsMessage.setMessage(response.toString());
            jmsSender.send("banco.out", jmsMessage);
        }
        catch (Exception e) {
            customExceptionHandler.handleException(e);
        }   
    }
    
    public double calculateServiceCost(String service, int minutes) {
        double costPerMinute = dao.getServiceCost(service);
        return costPerMinute * minutes;
    }

}
