package edu.java.helloworld.services;

import edu.java.helloworld.dto.request.UserRequestDTO;
import edu.java.helloworld.dto.response.Repository.UserDetailResponse;
import edu.java.helloworld.model.UserStatus;

public interface UserService{
    
    long saveUser(UserRequestDTO requestDTO);

    void updateUser(long userID, UserRequestDTO requestDTO);

    void changeStatus(long userID, UserStatus userStatus);

    void deleteUser(long userID);

    UserDetailResponse getUser(long userID);
}
