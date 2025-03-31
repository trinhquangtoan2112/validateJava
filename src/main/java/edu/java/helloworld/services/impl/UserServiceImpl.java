package edu.java.helloworld.services.impl;


import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.java.helloworld.dto.request.AddressRequestDTO;
import edu.java.helloworld.dto.request.UserRequestDTO;
import edu.java.helloworld.dto.response.Repository.UserDetailResponse;
import edu.java.helloworld.exception.ResourceNotFoundExecptionExecption;
import edu.java.helloworld.model.Address;
import edu.java.helloworld.model.User;
import edu.java.helloworld.model.UserStatus;
import edu.java.helloworld.model.UserType;
import edu.java.helloworld.repository.UserRepository;
import edu.java.helloworld.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;
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
}
