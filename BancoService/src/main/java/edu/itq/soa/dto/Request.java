package edu.itq.soa.dto;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    
    private String email;
    private int timeRequested; // in minutes
    private String streamingService;
    
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
