package com.springboot.Children;

import com.springboot.Man.Man;
import com.springboot.Man.ManRepository;
import com.springboot.Woman.Woman;
import com.springboot.Woman.WomanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenService {

    @Autowired
    private ChildrenRepository childrenRepository;

    @Autowired
    private ManRepository manRepository;

    @Autowired
    private WomanRepository womanRepository;

    public List<Children> getAllChildren(){
        return childrenRepository.findAll();
    }

    public List<Children> getChildrenByFirstName(String firstName){
        return childrenRepository.findByFirstName(firstName);
    }

    public List<Children> getChildrenByLastName(String lastName){
        return childrenRepository.findByLastName(lastName);
    }

    public Children getChildrenByName(String firstName,String lastName){
        return childrenRepository.findByFirstNameAndLastName(firstName,lastName);
    }

    public List<Children> getChildrenByAge(int age){
        return childrenRepository.findByAge(age);
    }

    public List<Children> getChildrenBySex(String sex){
        return childrenRepository.findBySex(sex);
    }

    public void addChildren(String firstName, String lastName, int age, String sex, Woman mother, Man man){
        Children child = new Children(firstName,lastName,age,sex,man,mother);
        childrenRepository.save(child);

    }

    public void updateChildrenName(String firstName, String lastName, String newFirst, String newLast){
        childrenRepository.updateChildrenNameByFirstNameAndLastName(newFirst,newLast,firstName,lastName);
    }

    public void updateChildrenAge(int age,String firstName, String lastName){
        childrenRepository.updateChildrenAgeByFirstNameAndLastName(age,firstName,lastName);
    }

    public void updateChildrenSex(String sex, String firstName,String lastName){
        childrenRepository.updateChildrenSexByFirstNameAndLastName(sex,firstName,lastName);
    }

    public void updateChildrenFather(Long fatherId, String firstName,String lastName){
        childrenRepository.updateChildrenFatherByFirstNameAndLastName(fatherId,firstName,lastName);
    }

    public void updateChildrenMother(Long motherId, String firstName, String lastName){
        childrenRepository.updateChildrenMotherByFirstNameAndLastName(motherId,firstName,lastName);
    }

}
