package edu.java.helloworld.dto.response.Repository;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetailResponse implements Serializable {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    
}
