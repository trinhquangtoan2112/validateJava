package edu.java.helloworld.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.java.helloworld.model.Gender;
import edu.java.helloworld.model.UserStatus;
import edu.java.helloworld.model.UserType;
import edu.java.helloworld.util.PhoneNumber;
import edu.java.helloworld.util.Regex;
import edu.java.helloworld.util.Gender.GenderAnotation;
import edu.java.helloworld.util.listEnum.EnumValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    
@NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;
     
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
    @EnumValue(name = "type", enumClass = UserType.class, message = "Khong phai type duoc cho")
    private String type;

    @NotNull(message = "username must be not null")
    private String username;

    @NotNull(message = "password must be not null")
    private String password;

    @NotEmpty(message = "addresses can not empty")
    private Set<AddressRequestDTO> addresses;

    public UserRequestDTO(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}
