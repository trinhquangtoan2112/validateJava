package edu.java.helloworld.dto.response.Repository;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDetailResponse implements Serializable {
   
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public UserDetailResponse(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
    


}
