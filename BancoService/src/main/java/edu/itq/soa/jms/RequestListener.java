package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import edu.itq.soa.business.BusinessLogic;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class RequestListener {
    
    @Autowired
    private BusinessLogic businessLogic;
    
    @JmsListener(destination = "banco.in")
    public void receive(Message message) {
        try 
        {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
       
            businessLogic.execute(jmsMessage);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
