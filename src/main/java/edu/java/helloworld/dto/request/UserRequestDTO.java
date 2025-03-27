package edu.java.helloworld.dto.request;

import java.io.Serializable;

import edu.java.helloworld.util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class UserRequestDTO implements Serializable {
    @NotBlank(message = "khong duoc de trong first name")
    private String firstName;
    @NotNull(message = "khong duoc bo trong")
    private String lastName;
    @Email(message = "phai la email nhat dinh")
    private String email;
   // @PhoneNumber // khong truyen value
    @PhoneNumber(value="dasadsdasdasd") // khong truyen value
    private String phone;


    public UserRequestDTO(String firstName,String lastName, String email, String phone){
      this.email=email;
      this.firstName=firstName;
      this.lastName=lastName;
      this.phone=phone;
    }
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
