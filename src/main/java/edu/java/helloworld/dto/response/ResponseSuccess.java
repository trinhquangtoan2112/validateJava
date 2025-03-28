package edu.java.helloworld.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;



public class ResponseSuccess extends ResponseEntity<ResponseSuccess.Payload> {



    //tra ve 2 tham so Put pacth delete ko tra ve du lieu tra ve message
    public ResponseSuccess(HttpStatusCode status,String messString) {
        super(new Payload(status.value(), messString),status);
    }
    // tra ve 3 tham so tra ve tham so
    public ResponseSuccess(HttpStatusCode status,String messString,Object data) {
        super(new Payload(status.value(), messString,data),status);
    }

    public static class Payload{
        private final int status;
        private final String message;
        private Object data;

        public Payload(int status ,String message) {
            this.status=status;
            this.message=message;
        }
        public Payload(int status ,String message,Object data) {
            this.status=status;
            this.message=message;
            this.data=data;
        }
        public Object getData() {
            return data;
        }
        public String getMessage() {
            return message;
        }
        public int getStatus() {
            return status;
        }
        public void setData(Object data) {
            this.data = data;
        }
        
    }
}
