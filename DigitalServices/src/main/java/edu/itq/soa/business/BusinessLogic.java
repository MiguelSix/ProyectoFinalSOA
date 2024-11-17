package edu.itq.soa.business;

import java.sql.SQLException;
import java.util.List;

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
            
            if (availableServices(request.getStreamingService())) {
                
                if (dao.isRegistered(request.getEmail(), request.getStreamingService())) {
                    String jsonResponse = gson.toJson(request);
                    jmsMessage.setMessage(jsonResponse);
                    jmsSender.send("banco.in", jmsMessage);     
                    
                } else {
                    response.setCode(403);
                    response.setEmail(request.getEmail());
                    response.setMsg("Usuario no registrado");
                }
                
            } else {
                response.setCode(404);
                response.setEmail(request.getEmail());
                response.setMsg("Servicio no disponible");
            }
            
            jmsMessage.setMessage(response.toString());
            jmsSender.send("servicios.out", jmsMessage);
            
        }
        catch (Exception e) {
            customExceptionHandler.handleException(e);
        }   
    }
    
    public boolean availableServices(String service) {
        List<String> services = dao.getServices();
        if (services.contains(service)) {
            return true;
        } 
        return false;
    }

}
