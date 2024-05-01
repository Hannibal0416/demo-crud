package com.example.demo.repository;

import com.example.demo.entity.Car;
import com.example.demo.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
  @Query("""
      SELECT c
      FROM Car c LEFT JOIN FETCH c.user
      """)
  List<Car> findAll(Pageable pageable);

  @Modifying(clearAutomatically = true)
  @Query("""
      UPDATE Car c
      SET c.user= :user
      WHERE c.id = :carId
      """)
  int updateUserIdById(@Param("user") User user, @Param("carId") Long carId);
}
