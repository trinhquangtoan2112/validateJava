package edu.java.helloworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.java.helloworld.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    
}
