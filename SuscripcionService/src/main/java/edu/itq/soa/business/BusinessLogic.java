package edu.itq.soa.business;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            
            int minutesPaid = dao.getMinutesPaid(request.getTransactionId());
            String streamingService = dao.getStreamingService(request.getTransactionId());
            updateExpireDate(minutesPaid, streamingService, request.getEmail());
            double totalPaid = dao.getTotalPaid(request.getTransactionId());
            System.out.println("Total pagado: " + totalPaid);
            
            response.setEmail(request.getEmail());
            response.setStreamingService(streamingService);
            response.setMinutesPaid(minutesPaid);
            response.setTotalPaid(totalPaid);
            response.setMsg("Hora de expiración en tu cuenta de " + streamingService + " actualizada con éxito."
                    + "¡Disfruta tus " + minutesPaid + " minutos disponibles");
            
            jmsMessage.setMessage(response.toString());
            jmsSender.send("suscripcion.out", jmsMessage);
        }
        catch (Exception e) {
            customExceptionHandler.handleException(e);
        }   
    }
    
    public void updateExpireDate(int minutesPaid, String streamingService, String email) {
        String expireDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(
                LocalDateTime.now().plusMinutes(minutesPaid));
        dao.updateExpireDate(streamingService, email, expireDate);
    }

}
