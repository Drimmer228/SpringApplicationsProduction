package com.example.springmodels.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cars")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Year is required")
    private int year;

    @ManyToOne
    @JoinColumn(name = "persons_id")
    private PersonModel owner;

    public CarModel() {}

    public CarModel(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public PersonModel getOwner() {
        return owner;
    }

    public void setOwner(PersonModel owner) {
        this.owner = owner;
    }
}
