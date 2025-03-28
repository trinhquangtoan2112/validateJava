package edu.java.helloworld.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExecptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class,ConstraintViolationException.class,HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleValidate(Exception e ,WebRequest request){
        System.out.println("=======>+MethodArgumentNotValidException");
               ErrorResponse errorResponse = new ErrorResponse();

               errorResponse.setTimestamp(new Date());
               errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
               errorResponse.setPath(request.getDescription(false).replace("uri=",""));
               errorResponse.setError("Bad request12323");

               String mess = e.getMessage();
               if( e instanceof MethodArgumentNotValidException){
 
                int start = mess.lastIndexOf("[");
                int end = mess.lastIndexOf("]");
                mess = mess.substring(start+1,end-1);
               }else{
                mess = mess.substring(mess.indexOf(" ")+1);
               }
            
              
               errorResponse.setMessage(mess);
               
               return errorResponse;
    };

    @ExceptionHandler({HandlerMethodValidationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(Exception e ,WebRequest request){
        System.out.println("=======>+handleServerError");
               ErrorResponse errorResponse = new ErrorResponse();

               errorResponse.setTimestamp(new Date());
               errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
               errorResponse.setPath(request.getDescription(false).replace("uri=",""));
               errorResponse.setError("Bad request12323");

               String mess = e.getMessage();
            
            
              
               errorResponse.setMessage(mess);
               
               return errorResponse;
    };
}
