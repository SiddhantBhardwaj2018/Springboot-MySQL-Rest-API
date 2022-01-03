package com.springboot.Woman;

import com.springboot.Children.Children;
import com.springboot.Man.Man;

import javax.persistence.*;

@Entity
public class Woman {


    @Id
    @SequenceGenerator(
            name = "female_sequence",
            sequenceName = "male_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "female_sequence"
    )
    private Long womanId;
    private String firstName;
    private String lastName;
    private int age;
    private boolean married;

    @OneToOne(
            optional = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "husbandId",
            referencedColumnName = "manId"
    )
    private Man husband;

    @OneToMany
    private Children child;

    public Woman(){

    }

    public Woman(String firstName, String lastName, int age,boolean married, Man husband){
        this.womanId = womanId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.married = married;
        this.husband = husband;
    }

    public Long getId(){
        return this.womanId;
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

    public Man getHusband(){
        return this.husband;
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

    public void setMaritalStatus(boolean married,Man man){
        if(married){
            this.married = married;
            this.husband = man;
        }else{
            this.married = married;
        }
    }
}
