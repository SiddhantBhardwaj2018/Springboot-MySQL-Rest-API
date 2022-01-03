package com.springboot.Man;

import com.springboot.Woman.Woman;
import com.springboot.Woman.WomanRepository;
import com.springboot.Woman.WomanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ManController {

    @Autowired
    private ManService manService;

    @Autowired
    private WomanService womanService;

    @Autowired
    private ManRepository manRepository;

    @Autowired
    private WomanRepository womanRepository;

    @RequestMapping("/Male/FirstName/{firstName}")
    public List<Man> getMaleByFirstName(@PathVariable String firstName){
        return manService.getMaleByFirstName(firstName);
    }

    @RequestMapping("/Male/LastName/{lastName}")
    public List<Man> getMaleByLastName(@PathVariable String lastName){
        return manService.getMaleByLastName(lastName);
    }

    @RequestMapping("/Male/Age/{age}")
    public List<Man> getMaleByAge(@PathVariable int age){
        return manService.getMaleByAge(age);
    }

    @RequestMapping("/Male/Name/{firstName}/{lastName}")
    public Man getMaleByName(@PathVariable String firstName, @PathVariable String lastName){
        return manService.getMaleByName(firstName,lastName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Male/Add")
    public void addMan(@RequestBody HashMap<String, String> man){
        if(!man.get("wifeFirst").equals("null")){
            Woman woman = womanRepository.findByFirstNameAndLastName(man.get("wifeFirst"),man.get("wifeLast"));
            Man male = new Man(man.get("firstName"),man.get("lastName"),Integer.valueOf(man.get("age")),false,null);
            manRepository.save(male);
            if(woman != null){
                womanRepository.save(woman);
                woman.setMaritalStatus(true,male);
                womanRepository.save(woman);
                male.setMaritalStatus(true,woman);
                manRepository.save(male);
            }else{
                Woman woman1 = new Woman(man.get("wifeFirst"),man.get("wifeLast"),Integer.valueOf(man.get("wifeAge")),false,null);
                womanRepository.save(woman1);
                male.setMaritalStatus(true,woman1);
                woman1.setMaritalStatus(true,male);
                manRepository.save(male);
                womanRepository.save(woman1);
            }
        }else{
            Man male = new Man(man.get("firstName"),man.get("lastName"),Integer.valueOf(man.get("age")), Boolean.valueOf(man.get("married")),null);
            manService.addMale(male);
        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Male/UpdateName/{firstName}/{lastName}")
    public void updateMan(@PathVariable  String firstName, @PathVariable String lastName,@RequestBody HashMap<String,String> map){
        manService.updateName(firstName,lastName,map.get("newFirst"),map.get("newLast"));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Male/UpdateAge/{firstName}/{lastName}")
    public void updateAge(@PathVariable String firstName,@PathVariable String lastName,@RequestBody HashMap<String,String> map){
        manService.updateAge(Integer.valueOf(map.get("age")),firstName,lastName);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Male/UpdateMarital/{firstName}/{lastName}")
    public void updateMarital(@RequestBody HashMap<String, String> map,  @PathVariable String firstName, @PathVariable String lastName){
        Man man = manRepository.findByFirstNameAndLastName(firstName,lastName);
        if(!map.get("wifeFirst").equals("null")){
            Woman woman = womanRepository.findByFirstNameAndLastName(map.get("wifeFirst"),map.get("wifeLast"));
            if(woman != null){
                manService.updateMaritalStatus(Boolean.valueOf(map.get("married")),woman,man);
                womanService.updateMaritalStatus(Boolean.valueOf(map.get("married")),man,woman);

            }else {
                Woman woman1 = new Woman(map.get("wifeFirst"),map.get("wifeLast"),Integer.valueOf(map.get("wifeAge")),true,man);
                womanRepository.save(woman1);
                manService.updateMaritalStatus(Boolean.valueOf(map.get("married")),woman1,man);
            }
        }else{
            manService.updateMaritalStatus(Boolean.valueOf(map.get("married")),null,man);
        }

    }
}
