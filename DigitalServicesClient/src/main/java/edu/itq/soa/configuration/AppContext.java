package edu.itq.soa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import edu.itq.soa.DigitalServicesClient;


@Configuration
public class AppContext {
    
    @Bean
    Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("edu.itq.soa.dto");
        return marshaller;
    }

    @Bean
    DigitalServicesClient ServiceClient(Jaxb2Marshaller marshaller) {
        DigitalServicesClient client = new DigitalServicesClient();
        client.setDefaultUri(DigitalServicesClient.URL);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
