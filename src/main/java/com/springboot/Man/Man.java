package com.springboot.Man;

import com.springboot.Children.Children;
import com.springboot.Woman.Woman;

import javax.persistence.*;

@Entity
public class Man {

    @Id
    @SequenceGenerator(
            name = "male_sequence",
            sequenceName = "male_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "male_sequence"
    )
    private Long manId;
    private String firstName;
    private String lastName;
    private int age;
    private boolean married;
    @OneToOne(
            optional = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
        name = "wifeId",
        referencedColumnName = "womanId"
    )
    private Woman wife;

    public Man(){

    }

    @OneToMany
    private Children child;

    public Man(String firstName, String lastName, int age, boolean married,Woman woman){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.married = married;
        this.wife = woman;
    }

    public Long getId(){
        return this.manId;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public int getAge(){
        return this.age;
    }

    public boolean getMarriedStatus(){
        return this.married;
    }

    public Woman getWife(){
        return this.wife;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setMaritalStatus(boolean married,Woman woman){
        if(married){
            this.married = married;
            this.wife = woman;
        }else{
            this.married = married;
        }
    }

}
