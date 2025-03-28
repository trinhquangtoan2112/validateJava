package edu.java.helloworld.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseData<T> {
    
    private final int status;
    private final  String message; 
    @JsonInclude(JsonInclude.Include.NON_NULL)// khi data khac null thi hien
    private T data;
   
    public ResponseData(int status,String messaString) {
     this.status=status;
     this.message= messaString;
    }
    public ResponseData(int status,String messaString,T data) {
        this.status=status;
        this.message= messaString;
        this.data=data;
       }

       public T getData() {
           return data;
       }
       public void setData(T data) {
           this.data = data;
       }
       public String getMessage() {
           return message;
       }
     
       public int getStatus() {
           return status;
       }
    
}
