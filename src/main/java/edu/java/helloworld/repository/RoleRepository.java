package edu.java.helloworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.java.helloworld.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select r from Role r inner join UserHasRole u on r.id = u.role.id where u.user.id= :user_id")
    List<Role> getAllByUserId(long user_id);
}
