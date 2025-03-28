package edu.java.helloworld.exception;

public class ResourceNotFoundExecptionExecption extends RuntimeException {
    public ResourceNotFoundExecptionExecption (String mess) {
        super(mess);
    }
    public ResourceNotFoundExecptionExecption (String mess,Throwable cause ) {
        super(mess,cause);
    }
}
