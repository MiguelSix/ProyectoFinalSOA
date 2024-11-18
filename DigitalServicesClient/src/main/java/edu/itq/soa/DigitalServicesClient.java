package edu.itq.soa;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.Response;

public class DigitalServicesClient extends WebServiceGatewaySupport{
    
    public static final String URL = "http://localhost:9080/DigitalServices";
    public static final String NAMESPACE = "http://itq.edu/soa";

    public Response DigitalServices(Request request) {
        return (Response) getWebServiceTemplate().marshalSendAndReceive(URL, request, new SoapActionCallback(NAMESPACE));
    }
}