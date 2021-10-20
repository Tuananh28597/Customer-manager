package com.codegym.cms.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;


public class CustomerForm {

    private Long id;

    @NotEmpty
    private String name;

    private String age;

    private MultipartFile image;

    public CustomerForm(Long id, String name, String age, MultipartFile image) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
    }

    public CustomerForm() {
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
