package br.com.javaapirestful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import org.bson.types.ObjectId;

public class User implements Serializable {

    public static final String COLLECTION = "users";
    
    @JsonIgnore
    private ObjectId id;
    
    private String name;
    
    private String email;
    
    @JsonIgnore
    private String password;       
    
    private String token;

    public User(String name, String email, String password, String token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
    }
    
    public User() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", token=" + token + '}';
    }    
    
}
