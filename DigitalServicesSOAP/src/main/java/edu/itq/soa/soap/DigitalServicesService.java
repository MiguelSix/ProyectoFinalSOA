package edu.itq.soa.soap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import edu.itq.soa.Request;
import edu.itq.soa.Response;
import edu.itq.soa.dao.Dao;

@Endpoint
public class DigitalServicesService {
    
    @Autowired
    private Dao dao;
    
    private static final String NAMESPACE = "http://itq.edu/soa";
    
    @PayloadRoot(namespace = NAMESPACE, localPart = "Request")
    @ResponsePayload
    public Response DigitalServices(@RequestPayload Request request) {
        Response response = new Response();
        String expireDate = dao.getExpireDate(request.getEmail(), request.getStreamingService());
        
        if (dao.login(request.getEmail(), request.getPassword())) {
            if (dao.serviceExists(request.getStreamingService())) {
                LocalDateTime expireDateLocal = LocalDateTime.parse(expireDate, 
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                if (expireDateLocal.isAfter(LocalDateTime.now())) {
                    response.setMessage("Bienvenido a " + request.getStreamingService() + ". Disfruta tus minutos.");
                } else {
                    response.setMessage("La suscripción ha expirado. Por favor, compra más minutos.");
                }   
            } else {
                response.setMessage("Servicio de Streaming inexistente");
            }
        } else {
            response.setMessage("Usuario o contraseña incorrectos");
        }
        
        return response;
    }

}
