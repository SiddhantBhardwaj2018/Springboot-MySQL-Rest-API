package com.springboot.Man;

import com.springboot.Man.Man;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ManRepository extends JpaRepository<Man, Long> {
    List<Man> findByFirstName(String firstName);

    List<Man> findByLastName(String lastName);

    List<Man> findByAge(int age);

    Man findByFirstNameAndLastName(String firstName, String lastName);

    List<Man> findByMarried(boolean married);

    @Modifying
    @Transactional
    @Query(
        value = "update man set first_name = ?1 , last_name = ?2 where first_name = ?3 AND last_name = ?4",
        nativeQuery = true
    )
    void updateManNameByFirstNameAndLastName(String newFirst, String newLast, String firstName,String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update man set age = ?1 where first_name = ?2 AND last_name = ?3",
            nativeQuery = true
    )
    void updateManAgeByFirstNameAndLastName(int Age, String firstName,String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update man set married = ?1, wife_id = ?2 where first_name = ?3 AND last_name = ?4",
            nativeQuery = true
    )
    void updateManMaritalStatusByFirstNameAndLastName(boolean married, Long wifeId  ,String firstName,String lastName);
}
