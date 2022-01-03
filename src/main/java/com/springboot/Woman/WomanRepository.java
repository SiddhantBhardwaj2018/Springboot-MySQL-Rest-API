package com.springboot.Woman;

import com.springboot.Man.Man;
import com.springboot.Woman.Woman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface WomanRepository extends JpaRepository<Woman,Long> {
    List<Woman> findByFirstName(String firstName);

    List<Woman> findByLastName(String lastName);

    List<Woman> findByAge(int age);

    Woman findByFirstNameAndLastName(String firstName, String lastName);

    List<Woman> findByMarried(boolean married);

    @Modifying
    @Transactional
    @Query(
            value = "update woman set first_name = ?1 , last_name = ?2 where first_name = ?3 AND last_name = ?4",
            nativeQuery = true
    )
    void updateWomanNameByFirstNameAndLastName(String newFirst, String newLast, String firstName,String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update woman set age = ?1 where first_name = ?2 AND last_name = ?3",
            nativeQuery = true
    )
    void updateWomanAgeByFirstNameAndLastName(int Age, String firstName,String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update woman set married = ?1, husband_id = ?2 where first_name = ?3 AND last_name = ?4",
            nativeQuery = true
    )
    void updateWomanMaritalStatusByFirstNameAndLastName(boolean married, Long husbandId  ,String firstName,String lastName);
}
