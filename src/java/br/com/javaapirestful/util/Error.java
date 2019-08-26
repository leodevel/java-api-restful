package br.com.javaapirestful.util;

import java.io.Serializable;

public class Error implements Serializable{
    
    private String message;
    
    private Error(String message) {
        this.message = message;
    }
    
    public static Error setError(String message){
        return new Error(message);
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
