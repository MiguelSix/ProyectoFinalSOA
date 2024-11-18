package edu.itq.soa.business;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import edu.itq.soa.DigitalServicesClient;
import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.Response;
import edu.itq.soa.jms.JmsSender;
import edu.itq.soa.tools.CustomExceptionHandler;

@Service
public class BusinessLogic {

    @Autowired
    private JmsSender jmsSender;
    
    @Autowired
    DigitalServicesClient client;
    
    @Autowired
    private CustomExceptionHandler customExceptionHandler;
    
    public void execute(JmsMessage jmsMessage) throws SQLException {
        Gson gson = new Gson();
        Request request = gson.fromJson(jmsMessage.getMessage(), Request.class);        
        Response response = new Response();
    
        try {
            
            response = client.DigitalServices(request);
            System.out.println("Message: " + response.getMessage());
            
            jmsMessage.setMessage(response.toString());
            jmsSender.send("inicio.out", jmsMessage);
        }
        catch (Exception e) {
            customExceptionHandler.handleException(e);
        }   
    }
}
