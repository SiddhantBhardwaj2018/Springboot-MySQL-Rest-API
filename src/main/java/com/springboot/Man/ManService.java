package com.springboot.Man;

import com.springboot.Man.Man;
import com.springboot.Man.ManRepository;
import com.springboot.Woman.Woman;
import com.springboot.Woman.WomanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ManService {

    @Autowired
    private ManRepository manRepository;

    @Autowired
    private WomanRepository womanRepository;

    public List<Man> getAllMales(){
        List<Man> males = manRepository.findAll();
        return males;
    }

    public List<Man> getMaleByFirstName(String firstName){
        return manRepository.findByFirstName(firstName);
    }

    public List<Man> getMaleByLastName(String lastName){
        return manRepository.findByLastName(lastName);
    }

    public List<Man> getMaleByAge(int age){
        return manRepository.findByAge(age);
    }

    public Man getMaleByName(String firstName, String lastName){
        return manRepository.findByFirstNameAndLastName(firstName,lastName);
    }

    public List<Man> getMaleByMaritalStatus(boolean married){
        return manRepository.findByMarried(married);
    }


    public void addMale(Man man){
        manRepository.save(man);
    }

    public void updateName(String firstName, String lastName,String newFirst, String newLast){
       manRepository.updateManNameByFirstNameAndLastName(newFirst,newLast,firstName,lastName);
    }

    public void updateAge(int age, String firstName, String lastName){
        manRepository.updateManAgeByFirstNameAndLastName(age,firstName,lastName);
    }

    public void updateMaritalStatus(boolean married, Woman woman, Man man){
        if(woman != null){
            manRepository.updateManMaritalStatusByFirstNameAndLastName(true,woman.getId(), man.getFirstName(), man.getLastName());
        }else{
            manRepository.updateManMaritalStatusByFirstNameAndLastName(false,null, man.getFirstName(), man.getLastName());
        }
    }


}
