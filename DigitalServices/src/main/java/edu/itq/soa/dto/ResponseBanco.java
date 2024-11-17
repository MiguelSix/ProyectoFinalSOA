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
public class ResponseBanco {
    private String status;
    private String email;
    private String transactionId;
    private String msg;
    
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}