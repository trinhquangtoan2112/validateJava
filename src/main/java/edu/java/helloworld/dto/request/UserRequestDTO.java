package edu.java.helloworld.dto.request;

import java.io.Serializable;

import edu.java.helloworld.model.Gender;
import edu.java.helloworld.model.UserStatus;
import edu.java.helloworld.model.UserType;
import edu.java.helloworld.util.PhoneNumber;
import edu.java.helloworld.util.Regex;
import edu.java.helloworld.util.Gender.GenderAnotation;
import edu.java.helloworld.util.listEnum.EnumValue;
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
    

     
    //Chi su dung cach nay cho String nhung neu dung thi khong the dung Enum nua
    // @Pattern(regexp = "^ACTIVE|INACTIVE|HOME$", message = "status mus be one in {ACTIVE, INACTIVE, NONE}")
    // private UserStatus userStatus;
     //Ưu điểm của phương pháp này là chúng ta có thể áp dụng chung cho tất cả các enum khác nhau:
    //Cach su dung enum
    @Regex(regexp = "ACTIVE|INACTIVE|NONE", name = "Status", message = "status mus be one in {ACTIVE, INACTIVE, NONE}")
    private UserStatus userStatus;

//Ưu điểm của phương pháp này là chúng ta có thể chỉ định cụ thể một vài giá trị cần validate trong enum thay vì tất cả.
    @GenderAnotation(anyOf = {Gender.MALE,Gender.FEMALE,Gender.OTHERS})
    private Gender gender;


    //Ưu điểm của phương pháp này là có thể áp dụng chung cho tất cả enum và dễ dàng handle exception.
    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)
    private String type;

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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
