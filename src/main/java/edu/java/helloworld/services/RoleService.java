package edu.java.helloworld.services;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.java.helloworld.model.Role;
import edu.java.helloworld.repository.RoleRepository;
import jakarta.annotation.PostConstruct;

@Service
public record RoleService(RoleRepository roleRepository) {

}
