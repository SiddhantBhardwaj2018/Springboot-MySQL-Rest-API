package com.springboot.Children;

import com.springboot.Man.Man;
import com.springboot.Man.ManRepository;
import com.springboot.Woman.Woman;
import com.springboot.Woman.WomanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ChildrenController {

    @Autowired
    private ChildrenService childrenService;

    @Autowired
    private ChildrenRepository childrenRepository;

    @Autowired
    private WomanRepository womanRepository;

    @Autowired
    private ManRepository manRepository;

    @RequestMapping("/children/all")
    public List<Children> getAllChildren(){
        return childrenService.getAllChildren();
    }

    @RequestMapping("/children/firstName/{firstName}")
    public List<Children> getChildrenbyFirstName(@PathVariable String firstName){
        return childrenService.getChildrenByFirstName(firstName);
    }

    @RequestMapping("/children/lastName/{lastName}")
    public List<Children> getChildrenByLastName(@PathVariable String lastName){
        return childrenService.getChildrenByLastName(lastName);
    }

    @RequestMapping("/children/name/{firstName}/{lastName}")
    public Children getChildrenByName(@PathVariable String firstName, @PathVariable String lastName){
        return childrenService.getChildrenByName(firstName,lastName);
    }

    @RequestMapping("/children/age/{age}")
    public List<Children> getChildrenByAge(@PathVariable int age){
        return childrenService.getChildrenByAge(age);
    }

    @RequestMapping("/children/sex/{sex}")
    public List<Children> getChildrenBySex(@PathVariable String sex){
        return childrenService.getChildrenBySex(sex);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/children/updateName/{firstName}/{lastName}")
    public void updateChildrenName(@PathVariable String firstName, @PathVariable String lastName, @RequestBody String newFirst, @RequestBody String newLast){
        childrenService.updateChildrenName(firstName,lastName,newFirst,newLast);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/children/updateAge/{firstName}/{lastName}")
    public void updateChildrenAge(@PathVariable String firstName,@PathVariable String lastName,@RequestBody HashMap<String,String> map){
        childrenService.updateChildrenAge(Integer.valueOf(map.get("age")),firstName,lastName);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/children/updateSex/{firstName}/{lastName}")
    public void updateChildrenSex(@PathVariable String firstName, @PathVariable String lastName, @RequestBody HashMap<String,String> map){
        childrenService.updateChildrenSex(map.get("sex"),firstName,lastName);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/children/add")
    public void addChild(@RequestBody HashMap<String,String> map){
        Woman mother = womanRepository.findByFirstNameAndLastName(map.get("motherFirst"),map.get("motherLast"));
        Man father = manRepository.findByFirstNameAndLastName(map.get("fatherFirst"),map.get("fatherLast"));
        if(mother != null && father != null){
            childrenService.addChildren(map.get("firstName"),map.get("lastName"),Integer.valueOf(map.get("age")),map.get("sex"),mother,father);
        }else if(mother != null){
            Man father1 = new Man(map.get("fatherFirst"),map.get("motherLast"),Integer.valueOf(map.get("fatherAge")),true,mother);
            manRepository.save(father1);
            womanRepository.updateWomanMaritalStatusByFirstNameAndLastName(true, father.getId(), mother.getFirstName(), mother.getLastName());
            childrenService.addChildren(map.get("firstName"),map.get("lastName"),Integer.valueOf(map.get("age")),map.get("sex"),mother,father1);
        }else if(father != null){
            Woman mother1 = new Woman(map.get("motherFirst"),map.get("motherLast"),Integer.valueOf(map.get("fatherAge")),true,father);
            womanRepository.save(mother1);
            manRepository.updateManMaritalStatusByFirstNameAndLastName(true, mother1.getId(), father.getFirstName(), father.getLastName());
            childrenService.addChildren(map.get("firstName"),map.get("lastName"),Integer.valueOf(map.get("age")),map.get("sex"),mother1,father);

        }else{
            Woman mother1 = new Woman(map.get("motherFirst"),map.get("motherLast"),Integer.valueOf(map.get("fatherAge")),false,null);
            Man father1 = new Man(map.get("fatherFirst"),map.get("motherLast"),Integer.valueOf(map.get("fatherAge")),false,null);
            womanRepository.save(mother1);
            manRepository.save(father1);
            womanRepository.updateWomanMaritalStatusByFirstNameAndLastName(true, father1.getId(), mother.getFirstName(), mother.getLastName());
            manRepository.updateManMaritalStatusByFirstNameAndLastName(true, mother1.getId(), father1.getFirstName(), father1.getLastName());
            childrenService.addChildren(map.get("firstName"),map.get("lastName"),Integer.valueOf(map.get("age")),map.get("sex"),mother1,father1);
        }

    }

    @RequestMapping("/children/updateFather/{firstName}/{lastName}")
    public void updateFather(@RequestBody HashMap<String,String> map, @PathVariable String firstName,@PathVariable String lastName){
        String fatherFirst = map.get("fatherFirst");
        String fatherLast = map.get("fatherLast");
        Man father = manRepository.findByFirstNameAndLastName(fatherFirst,fatherLast);
        if(father != null){
            childrenRepository.updateChildrenFatherByFirstNameAndLastName(father.getId(),firstName,lastName);
        }else{
            Woman wife = womanRepository.findByFirstNameAndLastName(map.get("motherFirst"),map.get("motherLast"));
            if(wife != null){
                Man father1 = new Man(fatherFirst,fatherLast,Integer.valueOf(map.get("fatherAge")),true,wife);
                manRepository.save(father1);
                womanRepository.updateWomanMaritalStatusByFirstNameAndLastName(true, father1.getId(), wife.getFirstName(), wife.getLastName());
                childrenRepository.updateChildrenFatherByFirstNameAndLastName(father1.getId(),firstName,lastName);
            }else{
                Woman mother = new Woman(map.get("motherFirst"),map.get("motherLast"),Integer.valueOf(map.get("motherAge")),false,null);
                Man father1 = new Man(fatherFirst,fatherLast,Integer.valueOf(map.get("fatherAge")),false,null);
                manRepository.save(father1);
                womanRepository.save(mother);
                manRepository.updateManMaritalStatusByFirstNameAndLastName(true,mother.getId(),fatherFirst,fatherLast);
                womanRepository.updateWomanMaritalStatusByFirstNameAndLastName(true, father1.getId(), mother.getFirstName(), mother.getLastName());
                childrenRepository.updateChildrenFatherByFirstNameAndLastName(father1.getId(),firstName,lastName);
                childrenRepository.updateChildrenMotherByFirstNameAndLastName(mother.getId(), firstName,lastName);
            }
        }
    }

    @RequestMapping("/children/updateMother/{firstName}/{lastName}")
    public void updateMother(@RequestBody HashMap<String,String> map, @PathVariable String firstName,@PathVariable String lastName){
        String motherFirst = map.get("motherFirst");
        String motherLast = map.get("motherLast");
        Woman mother = womanRepository.findByFirstNameAndLastName(motherFirst,motherLast);
        if(mother != null){
            childrenRepository.updateChildrenFatherByFirstNameAndLastName(mother.getId(),firstName,lastName);
        }else{
            Man husband = manRepository.findByFirstNameAndLastName(map.get("fatherFirst"),map.get("fatherLast"));
            if(husband != null){
                Woman mother1 = new Woman(motherFirst,motherLast,Integer.valueOf(map.get("motherAge")),true,husband);
                womanRepository.save(mother1);
                womanRepository.updateWomanMaritalStatusByFirstNameAndLastName(true, mother1.getId(), husband.getFirstName(), husband.getLastName());
                childrenRepository.updateChildrenFatherByFirstNameAndLastName(mother1.getId(),firstName,lastName);
            }else{
                Woman mother1 = new Woman(map.get("motherFirst"),map.get("motherLast"),Integer.valueOf(map.get("motherAge")),false,null);
                Man father1 = new Man(map.get("fatherFirst"),map.get("fatherLast"),Integer.valueOf(map.get("fatherAge")),false,null);
                manRepository.save(father1);
                womanRepository.save(mother1);
                manRepository.updateManMaritalStatusByFirstNameAndLastName(true,mother1.getId(), father1.getFirstName(), father1.getLastName());
                womanRepository.updateWomanMaritalStatusByFirstNameAndLastName(true, father1.getId(), mother1.getFirstName(), mother.getLastName());
                childrenRepository.updateChildrenFatherByFirstNameAndLastName(mother1.getId(),firstName,lastName);
                childrenRepository.updateChildrenFatherByFirstNameAndLastName(father1.getId(),firstName,lastName);

            }
        }
    }
}
