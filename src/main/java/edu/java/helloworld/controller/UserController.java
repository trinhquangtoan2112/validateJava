package edu.java.helloworld.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.java.helloworld.dto.request.UserRequestDTO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/")
    public String addUser( @Valid @RequestBody UserRequestDTO userRequestDTO) {
        
        UserRequestDTO userDTO = new UserRequestDTO(userRequestDTO.getFirstName(), userRequestDTO.getLastName(), userRequestDTO.getEmail(),userRequestDTO.getPhone(), userRequestDTO.getUserStatus());
      System.out.println(userDTO.getFirstName());
        return "da nhan";
    }
    
}
