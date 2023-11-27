package com.example.springmodels.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="persons")
public class PersonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Age is required")
    private int age;

    private String email;

    @ManyToMany
    @JoinTable(
            name = "person_house",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    private List<HouseModel> houses;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<CarModel> cars;

    public PersonModel() {}

    public PersonModel(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }

    public List<HouseModel> getHouses() {
        return houses;
    }

    public void setHouses(List<HouseModel> houses) {
        this.houses = houses;
    }
}
