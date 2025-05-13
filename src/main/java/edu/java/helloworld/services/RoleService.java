package edu.java.helloworld.services;

import org.springframework.stereotype.Service;

import edu.java.helloworld.repository.RoleRepository;

@Service
public record RoleService(RoleRepository roleRepository) {

}
