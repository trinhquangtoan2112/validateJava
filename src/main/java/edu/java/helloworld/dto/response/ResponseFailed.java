package edu.java.helloworld.dto.response;

import org.springframework.http.HttpStatusCode;

public class ResponseFailed extends ResponseSuccess {

    public ResponseFailed(HttpStatusCode status, String messString) {
        super(status, messString);
       
    }
    
}
