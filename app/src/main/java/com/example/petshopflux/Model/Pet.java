package com.example.petshopflux.Model;

import java.math.BigInteger;
import java.util.List;

public class Pet {

    private BigInteger id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private String status;

    public Pet(BigInteger id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }
}
