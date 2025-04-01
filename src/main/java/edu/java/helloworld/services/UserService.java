package edu.java.helloworld.services;

import java.util.List;

import edu.java.helloworld.dto.request.UserRequestDTO;
import edu.java.helloworld.dto.response.Repository.UserDetailResponse;
import edu.java.helloworld.dto.response.page.PageRespones;
import edu.java.helloworld.model.UserStatus;

public interface UserService{
    
    long saveUser(UserRequestDTO requestDTO);

    void updateUser(long userID, UserRequestDTO requestDTO);

    void changeStatus(long userID, UserStatus userStatus);

    void deleteUser(long userID);

    UserDetailResponse getUser(long userID);
    List<UserDetailResponse> getAllUser(int pageSize,int pageNo,String sortBy);

    PageRespones<?> getAllUserWithMultiColoum(int pageSize,int pageNo,String... sortBy);

    PageRespones<?> getAllUserWithColoumandSearch(int pageSize,int pageNo,String search,String sortBy);
    PageRespones<?> advancedSearch(int pageSize,int pageNo,String sortBy,String address,String... search);

}
