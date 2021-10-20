package com.codegym.cms.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy  = GenerationType.AUTO)
    private Long id;

    private String name;
    private String age;
    private String Image;


    public Customer(Long id, String name, String age, String image) {
        this.id = id;
        this.name = name;
        this.age = age;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }



    public Customer() {

    }

    @Override
    public String toString(){
        return String.format("Customer[id=%d, name='%s',age='%s']", id , getName(), age);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
