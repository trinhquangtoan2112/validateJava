package edu.java.helloworld.services.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.java.helloworld.dto.request.AddressRequestDTO;
import edu.java.helloworld.dto.request.UserRequestDTO;
import edu.java.helloworld.dto.response.Repository.UserDetailResponse;
import edu.java.helloworld.dto.response.page.PageRespones;
import edu.java.helloworld.exception.ResourceNotFoundExecptionExecption;
import edu.java.helloworld.model.Address;
import edu.java.helloworld.model.User;
import edu.java.helloworld.model.UserStatus;
import edu.java.helloworld.model.UserType;
import edu.java.helloworld.repository.SearchRepository;
import edu.java.helloworld.repository.UserRepository;
import edu.java.helloworld.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;
   private final SearchRepository searchRepository;
    @Override
    public long saveUser(UserRequestDTO request) {
      log.info(request.getFirstName());
      User user = User.builder()
      .firstName(request.getFirstName())
      .lastName(request.getLastName())
      .dateOfBirth(request.getDateOfBirth())
      .gender(request.getGender())
      .phone(request.getPhone())
      .email(request.getEmail())
      .username(request.getUsername())
      .password(request.getPassword())
      .status(request.getUserStatus())
      .type(UserType.valueOf(request.getType().toUpperCase()))
      .addresses(convertToAddress(request.getAddresses()))
      .build();
      request.getAddresses().forEach(a ->
                user.saveAddress(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .build()));
      userRepository.save(user);
      log.info("User has save succefully");
      return user.getId();
    }

    @Override
    public void updateUser(long userID, UserRequestDTO requestDTO) {
      getUserById(userID);
    }

    @Override
    public void changeStatus(long userID, UserStatus userStatus) {
      getUserById(userID);
    }

    @Override
    public void deleteUser(long userID) {
      getUserById(userID);
    }

    @Override
    public UserDetailResponse getUser(long userID) {
        User user = getUserById(userID);
        UserDetailResponse userDetail = UserDetailResponse.builder().email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).phone(user.getPhone()).build();
        return userDetail;
    }

     private Set<Address> convertToAddress(Set<AddressRequestDTO> addresses) {
        Set<Address> result = new HashSet<>();
        addresses.forEach(a ->
                result.add(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .build())
        );
        return result;
    }

    public User getUserById(long userId){
      return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundExecptionExecption("Khong tim thay id"));
    }

    @Override
    public List<UserDetailResponse> getAllUser(int pageSize,int pageNo,String sortBy) {
      int p =0;
      if(pageNo>0){
        p =pageNo-1;
      }
      
      List<Sort.Order> sort = new ArrayList<>();
    
        if(StringUtils.hasLength(sortBy)){
          Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
          Matcher matcher = pattern.matcher(sortBy);
          if(matcher.find()){
  if(matcher.group(3).equalsIgnoreCase("asc")){
    sort.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
  }else{
    sort.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
  }
          }
        
      }
  
    
    Pageable pageable = PageRequest.of(p, pageSize,Sort.by(sort));
    Page<User> listUser = userRepository.findAll(pageable);
    //  Page<User> listUser = userRepository.findAll()
    List<UserDetailResponse> listUserDetail = listUser.stream().map(user-> UserDetailResponse.builder()
      .email(user.getEmail()).firstName(user.getFirstName())
      .lastName(user.getLastName()).phone(user.getPhone()).build()).toList();
    return listUserDetail;
    }
    @Override
    public PageRespones<?> getAllUserWithMultiColoum(int pageSize,int pageNo,String... sortBy) {
      int p =0;
      if(pageNo>0){
        p =pageNo-1;
      }
      
      List<Sort.Order> sort = new ArrayList<>();
      for (String s : sortBy) {
        log.info(s,"sssks");
        if(StringUtils.hasLength(s)){
          Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
          Matcher matcher = pattern.matcher(s);
          if(matcher.find()){
  if(matcher.group(3).equalsIgnoreCase("asc")){
    sort.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
  }else{
    sort.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
  }
          }
        }
      }
  
    
     Pageable pageable = PageRequest.of(p, pageSize,Sort.by(sort));
    Page<User> listUser = userRepository.findAll(pageable);
    //  Page<User> listUser = userRepository.findAll()
    List<UserDetailResponse> list= listUser.stream()
    .map(user -> UserDetailResponse.builder().email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).phone(user.getPhone()).build()
    ).toList();
      PageRespones<?> listUserDetail = PageRespones.builder().pageNo(pageNo).pageSize(pageSize)
      .totalPage(listUser.getTotalPages()).item(list).build();
      
    return listUserDetail;
    }

    @Override
    public PageRespones<?> getAllUserWithColoumandSearch(int pageSize, int pageNo,String search, String sortBy) {
     
      return searchRepository.getAllUserWithColoumandSearch( pageSize,  pageNo, search,  sortBy);
    }

    @Override
    public PageRespones<?> advancedSearch(int pageSize, int pageNo, String sortBy,String address, String... search) {
      PageRespones<?> pageRespones= searchRepository.advancedSearch( pageSize,  pageNo, sortBy,address,  search);
      return pageRespones;
    }
    
}
