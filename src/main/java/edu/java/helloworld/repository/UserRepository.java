package edu.java.helloworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.java.helloworld.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    // hai cai nay ngang nhayu lay gia tri distint
    // @Query("Select distinst from User u where u.firstName = firstName and
    // u.lastName = lastName")
    // List<User> findDistinstFromFirstNameAndLastName(String firstName,String
    // lastName);

    // @Query("Select * from User u where u.firstName like firstName ")
    // List<User> findByFirstName(String firstName);

    // OR
    // @Query("Select * from User u where u.firstName like firstName or
    // u.lastName like lastName")
    // List<User> findDistinstFromFirstNameOrLastName(String firstName,String
    // lastName);

    // Is, Equals
    // @Query("Select * from User u where u.firstName = firstName ")
    // List<User> findByFirstNameEquals(String firstName);
    // List<User> findByFirstNameIs(String firstName);
    // List<User> findByFirstName(String firstName);

    // Between
    // @Query("Select * from User u where u.createAt between 1 and 2 ")
    // List<User> findByCreateAtBetween(Date startDate,Date endDate);

    // lessthan
    // @Query("Select * from User u where u.age <age")
    // List<User> findByAgeLessThan(int age);
    // List<User> findByAgeGreaterThan(int age);
    // @Query("Select * from User u where u.age <=age")
    // List<User> findByAgeLessThanEquals(int age);
    // List<User> findByAgeGreaterThanEquals(int age);

    // Before && After
    // @Query("Select * from User u where u.createAt <date")
    // List<User> findByCreateAtBefore(Date date);
    // List<User> findByCreateAtAfter(Date date);

    // Null,NotNull
    // @Query("Select * from User u where u.age is not null")
    // List<User> findByAgeNotNull(int age);
    // @Query("Select * from User u where u.age is null")
    // List<User> findByAgeIsNull(int age);

    // Like
    // @Query("Select * from User u where u.first like %firstName%")
    // List<User> findByFirstNameLike(String firstName);
    // Not Like
    // @Query("Select * from User u where u.first not like %firstName%")
    // List<User> findByFirstNameNotLike(String firstName);

    // Starting with
    // @Query("Select * from User u where u.first like firstName%")
    // List<User> findByFirstNameStartingWith(String firstName);
    // Ending with
    // @Query("Select * from User u where u.first like %firstName")
    // List<User> findByFirstNameEndingWith(String firstName);

    // Containing
    // @Query("Select * from User u where u.first like %firstName%")
    // List<User> findByFirstNameContaining(String firstName);

    // Not
    // @Query("Select * from User u where u.first <> firstName")
    // List<User> findByFirstNameNot(String firstName);

    // In
    // @Query("Select * from User u where u.age in (18,25,30) ")
    // List<User> findByAgeIn(Collection<Integer> ages);

    // Not In
    // @Query("Select * from User u where u.age not in (18,25,30) ")
    // List<User> findByAgeNotIn(Collection<Integer> ages);

    // True /false
    // @Query("Select * from User u where u.active =true ")
    // List<User> findByActivatedTrue();
    // List<User> findByActivatedFalse();
    // List<User> findByActivated(Boolean active);

    // IgnoreCase
    // @Query("Select * from User u where LOWER(u.firtsName) = LOWER(firstName) ")
    // List<User> findByFirstNameIgnoreCase(String firstName);

    // List<User> findByFirstNameOrderByCreateAtDesc(String firtsName)
    // List<User> findByFirstNameOrderByCreateAtAsc(String firtsName)
    // List<User> findByFirstNameAndLastNameAllIgnore(String firtsName,String
    // lastName)

    // viet native query
    @Query(value = "select * from tbl_user", nativeQuery = true)
    List<User> getAllUser();

    @Query(value = "select * from tbl_user u inner join tbl_address a  on u.id= a.userId where a.city =:city", nativeQuery = true)
    List<User> getUserByCity(String city);
}
