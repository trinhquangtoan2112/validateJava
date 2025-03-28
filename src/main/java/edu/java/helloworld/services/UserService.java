package edu.java.helloworld.services;



import edu.java.helloworld.dto.request.UserRequestDTO;


public interface UserService {
    int addUser(UserRequestDTO userRequestDTO);
}
