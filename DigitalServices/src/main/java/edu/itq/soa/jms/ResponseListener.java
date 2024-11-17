package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.ResponseSuscripcion;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class ResponseListener {
    
    @Autowired
    private JmsSender jmsSender;
    
    @JmsListener(destination = "suscripcion.out")
    public void receive(Message message) {
        try 
        {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            
            Gson gson = new Gson();
            ResponseSuscripcion request = gson.fromJson(jmsMessage.getMessage(), ResponseSuscripcion.class);        
            
            jmsMessage.setMessage(request.toString());
            jmsSender.send("servicios.out", jmsMessage);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
