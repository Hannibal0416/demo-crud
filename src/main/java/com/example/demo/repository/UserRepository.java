package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @EntityGraph(attributePaths = {"cars"})
    Iterable<User> findAll(Pageable pageable);

    @Query("""
            SELECT u 
            FROM User u LEFT JOIN FETCH u.cars
            """)
    List<User> findAllUsers(Pageable pageable);
    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE User u 
            SET u.name= :#{#user.name}, u.age = :#{#user.age} 
            WHERE u.id = :#{#user.id}
          """)
    int updateUserById(@Param("user")User user);




}
