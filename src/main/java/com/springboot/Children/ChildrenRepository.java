package com.springboot.Children;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ChildrenRepository extends JpaRepository<Children,Long> {

    List<Children> findByFirstName(String firstName);
    List<Children> findByLastName(String lastName);
    List<Children> findBySex(String sex);
    List<Children> findByAge(int age);

    Children findByFirstNameAndLastName(String firstName, String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update children set first_name = ?1 , last_name = ?2 where first_name = ?3 AND last_name = ?4",
            nativeQuery = true
    )
    public void updateChildrenNameByFirstNameAndLastName(String newFirst, String newLast, String firstName, String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update children set age = ?1 where first_name = ?2 AND last_name = ?3",
            nativeQuery = true
    )
    public void updateChildrenAgeByFirstNameAndLastName(int age, String firstName,String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update children set sex = ?1 where first_name = ?2 AND last_name = ?3",
            nativeQuery = true
    )
    public void updateChildrenSexByFirstNameAndLastName(String sex, String firstName, String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update children set mother_id = ?1 where first_name = ?2 AND last_name = ?3",
            nativeQuery = false
    )
    public void updateChildrenMotherByFirstNameAndLastName(Long motherId, String firstName, String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "update children set father_id = ?1 where first_name = ?2 AND last_name = ?3",
            nativeQuery = true
    )
    public void updateChildrenFatherByFirstNameAndLastName(Long fatherId, String firstName, String lastName);

    List<Children> findAll();

}
