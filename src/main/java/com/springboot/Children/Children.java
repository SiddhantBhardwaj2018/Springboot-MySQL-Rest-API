package com.springboot.Children;

import com.springboot.Man.Man;
import com.springboot.Woman.Woman;

import javax.persistence.*;

@Entity
public class Children {

    @Id
    @SequenceGenerator(
            name = "children_sequence",
            sequenceName = "children_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "children_sequence"
    )
    private Long childrenId;
    private String childFirstName;
    private String childLastName;
    private int age;
    private String sex;

    @ManyToOne(
            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(
            name = "motherId",
            referencedColumnName = "womanId"
    )
    private Woman mother;

    @ManyToOne(
            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(
            name = "fatherId",
            referencedColumnName = "manId"
    )
    private Man father;

    public Children(){

    }

    public Children(String childFirstName, String childlastName, int age, String sex, Man man, Woman woman){
        this.childFirstName = childFirstName;
        this.childLastName = childlastName;
        this.age = age;
        this.sex = sex;
        this.mother = woman;
        this.father = man;
    }

    public String getChildFirstName(){
        return this.childFirstName;
    }

    public String getChildLastName(){
        return this.childLastName;
    }

    public int getAge() {
        return this.age;
    }

    public Long getChildrenId() {
        return childrenId;
    }

    public String getSex(){
        return this.sex;
    }

    public Man getFather(){
        return this.father;
    }

    public Woman getMother(){
        return this.mother;
    }

    public void setChildFirstName(String firstName){
        this.childFirstName = firstName;
    }

    public void setChildLastName(){
        this.childLastName = childLastName;
    }

    public void  setAge(int Age){
        this.age = Age;
    }

    public void setSex(String Sex){
        this.sex = Sex;
    }

    public void setMother(Woman woman){
        this.mother = mother;
    }

    public void setFather(Man man){
        this.father = father;
    }
}
