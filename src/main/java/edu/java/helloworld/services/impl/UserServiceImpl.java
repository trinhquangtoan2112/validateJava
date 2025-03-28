package edu.java.helloworld.services.impl;

import org.springframework.stereotype.Service;

import edu.java.helloworld.dto.request.UserRequestDTO;
import edu.java.helloworld.exception.ResourceNotFoundExecptionExecption;
import edu.java.helloworld.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public int addUser(UserRequestDTO userRequestDTO) {
       System.out.println("Add user");
       if(!userRequestDTO.getFirstName().equals("Tay")){
        throw new ResourceNotFoundExecptionExecption("Khong tim thay nguoi dung");
       }
        return 0;
    }
    
}