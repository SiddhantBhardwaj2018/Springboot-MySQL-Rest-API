package com.springboot.Woman;

import com.springboot.Man.Man;
import com.springboot.Man.ManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WomanService {

    @Autowired
    private WomanRepository womanRepository;

    @Autowired
    private ManRepository manRepository;

    public List<Woman> getAllFemales(){
        List<Woman> females = womanRepository.findAll();
        return females;
    }

    public List<Woman> getFemaleByFirstName(String firstName){
        return womanRepository.findByFirstName(firstName);
    }

    public List<Woman> getFemaleByLastName(String lastName){
        return womanRepository.findByLastName(lastName);
    }

    public List<Woman> getFemaleByAge(int age){
        return womanRepository.findByAge(age);
    }

    public Woman getFemaleByName(String firstName, String lastName){
        return womanRepository.findByFirstNameAndLastName(firstName,lastName);
    }

    public List<Woman> getFemaleByMaritalStatus(boolean married){
        return womanRepository.findByMarried(married);
    }

    public void addFemale(Woman woman){
        womanRepository.save(woman);
    }

    public void updateName(String firstName, String lastName,String newFirst, String newLast){
        womanRepository.updateWomanNameByFirstNameAndLastName(newFirst,newLast,firstName,lastName);
    }

    public void updateAge(int age, String firstName, String lastName){
        womanRepository.updateWomanAgeByFirstNameAndLastName(age,firstName,lastName);
    }

    public void updateMaritalStatus(boolean married, Man man, Woman woman){
        if(woman != null){
            womanRepository.updateWomanMaritalStatusByFirstNameAndLastName(true,man.getId(), woman.getFirstName(), woman.getLastName());
        }else{
            womanRepository.updateWomanMaritalStatusByFirstNameAndLastName(false,null, woman.getFirstName(), woman.getLastName());
        }
    }
}
