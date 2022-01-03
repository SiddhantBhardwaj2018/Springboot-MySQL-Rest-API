package com.springboot.Woman;

import com.springboot.Man.Man;
import com.springboot.Man.ManRepository;
import com.springboot.Man.ManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class WomanController {

    @Autowired
    private WomanService womanService;

    @Autowired
    private  ManService manService;

    @Autowired
    private WomanRepository womanRepository;

    @Autowired
    private ManRepository manRepository;

    @RequestMapping("/Female/FirstName/{firstName}")
    public List<Woman> getFemaleByFirstName(@PathVariable String firstName){
        return womanService.getFemaleByFirstName(firstName);
    }

    @RequestMapping("/Female/LastName/{lastName}")
    public List<Woman> getFemaleByLastName(@PathVariable String lastName){
        return womanService.getFemaleByLastName(lastName);
    }

    @RequestMapping("/Female/Age/{age}")
    public List<Woman> getFemaleByAge(@PathVariable int age){
        return womanService.getFemaleByAge(age);
    }

    @RequestMapping("/Female/Name/{firstName}/{lastName}")
    public Woman getFemaleByName(@PathVariable String firstName, @PathVariable String lastName){
        return womanService.getFemaleByName(firstName,lastName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Female/Add")
    public void addWoman(@RequestBody HashMap<String, String> woman){
       if(!woman.get("husbandFirst").equals("null")){
           Man man = manRepository.findByFirstNameAndLastName(woman.get("husbandFirst"),woman.get("husbandLast"));
           Woman female = new Woman(woman.get("firstName"),woman.get("lastName"),Integer.valueOf(woman.get("age")), false, null);
           womanRepository.save(female);
           if(man != null){
               manRepository.save(man);
               man.setMaritalStatus(true, female);
               manRepository.save(man);
               female.setMaritalStatus(true,man);
               womanRepository.save(female);
           }else{
                Man man1 = new Man(woman.get("husbandFirst"),woman.get("husbandLast"),Integer.valueOf(woman.get("husbandAge")),Boolean.valueOf(woman.get("married")),null);
                manRepository.save(man1);
                female.setMaritalStatus(true,man1);
                man1.setMaritalStatus(true,female);
                womanRepository.save(female);
                manRepository.save(man1);
           }
       }else{
           Woman female = new Woman(woman.get("firstName"),woman.get("lastName"),Integer.valueOf(woman.get("age")),Boolean.valueOf(woman.get("married")),null);
           womanService.addFemale(female);
       }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Female/UpdateName/{firstName}/{lastName}")
    public void updateMan(@PathVariable  String firstName, @PathVariable String lastName,@RequestBody HashMap<String,String> map){
        womanService.updateName(firstName,lastName,map.get("newFirst"),map.get("newLast"));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Female/UpdateAge/{firstName}/{lastName}")
    public void updateAge(@PathVariable String firstName,@PathVariable String lastName,@RequestBody HashMap<String,String> map){
        womanService.updateAge(Integer.valueOf(map.get("age")),firstName,lastName);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Female/UpdateMarital/{firstName}/{lastName}")
    public void updateMarital(@RequestBody HashMap<String, String> map,  @PathVariable String firstName, @PathVariable String lastName){
        Woman woman = womanRepository.findByFirstNameAndLastName(firstName,lastName);
        if(!map.get("husbandFirst").equals("null")){
            Man man = manRepository.findByFirstNameAndLastName(map.get("husbandFirst"),map.get("husbandLast"));
            if(man !=  null){
                womanService.updateMaritalStatus(Boolean.valueOf(map.get("married")),man,woman);
                manService.updateMaritalStatus(Boolean.valueOf(map.get("married")),woman,man);
            }else{
                Man man1 = new Man(map.get("husbandFirst"),map.get("husbandLast"),Integer.valueOf(map.get("husbandAge")),true, woman);
                manRepository.save(man1);
                womanService.updateMaritalStatus(Boolean.valueOf(map.get("married")),man1,woman);
            }
        }else{
                womanService.updateMaritalStatus(Boolean.valueOf(map.get("married")),null,woman);
        }
    }
}
