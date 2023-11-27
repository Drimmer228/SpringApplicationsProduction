package com.example.springmodels.controllers;

import com.example.springmodels.models.CarModel;
import com.example.springmodels.models.HouseModel;
import com.example.springmodels.models.PersonModel;
import com.example.springmodels.repositories.CarRepository;
import com.example.springmodels.repositories.HouseRepository;
import com.example.springmodels.repositories.PersonRepository;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;
    private final HouseRepository houseRepository;

    public PersonController(PersonRepository personRepository, CarRepository carRepository, HouseRepository houseRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
        this.houseRepository = houseRepository;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<PersonModel> persons = personRepository.findAll();
        model.addAttribute("persons", persons);
        return "persons/index";
    }

    @GetMapping("/new")
    public String newPersonForm(Model model) {
        model.addAttribute("person", new PersonModel());
        model.addAttribute("cars", carRepository.findAll());
        return "persons/new";
    }

    @PostMapping
    public String createPerson(@Valid @ModelAttribute("person") PersonModel person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "persons/new";
        }
        personRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/edit")
    public String editPersonForm(@PathVariable long id, Model model) {
        Optional<PersonModel> person = personRepository.findById(id);

        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("houses", houseRepository.findAll());
        model.addAttribute("person", person.orElse(null));
        return "persons/edit";
    }

    @GetMapping("/{id}/modifyCarAtPerson")
    public String modifyCarAtPersonForm(@PathVariable long id, Model model) {
        Optional<PersonModel> person = personRepository.findById(id);
        model.addAttribute("person", person.orElse(null));
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("houses", houseRepository.findAll());
        return "persons/modifyCarAtPerson";
    }

    @PostMapping("/{id}/modifyCarAtPerson")
    public String modifyCarAtPerson(@PathVariable long id, @RequestParam("carId") Long carId) {
        Optional<PersonModel> person = personRepository.findById(id);
        Optional<CarModel> car = carRepository.findById(carId);

        if (person.isPresent() && car.isPresent()) {
            CarModel carModel = car.get();
            carModel.setOwner(person.get());
            carRepository.save(carModel);
        }

        return "redirect:/persons/{id}/modifyCarAtPerson";
    }

    @PostMapping("/{personId}/removeCar")
    public String removeCarFromPerson(@PathVariable("personId") long personId, @RequestParam("carId") long carId) {
        Optional<PersonModel> person = personRepository.findById(personId);
        Optional<CarModel> car = carRepository.findById(carId);

        if (person.isPresent() && car.isPresent()) {
            CarModel carModel = car.get();
            carModel.setOwner(null);
            carRepository.save(carModel);
        }

        return "redirect:/persons/{personId}/modifyCarAtPerson";
    }

    @PostMapping("/update/{id}")
    public String updatePerson(@PathVariable int id, @Valid @ModelAttribute("person") PersonModel person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "persons/edit";
        }
        personRepository.save(person);
        return "redirect:/persons";
    }

    @PostMapping("/delete/{id}")
    public String deletePerson(@PathVariable long id) {
        personRepository.deleteById(id);
        return "redirect:/persons";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable long id, Model model) {
        Optional<PersonModel> person = personRepository.findById(id);
        model.addAttribute("person", person.orElse(null));
        return "persons/show";
    }

    @GetMapping("/search")
    public String searchByName(@RequestParam("name") String name, Model model) {
        List<PersonModel> personByName = personRepository.findByName(name);
        model.addAttribute("persons", personByName);
        return "persons/search_results";
    }

    @GetMapping("/{id}/cars")
    public String getPersonCars(@PathVariable long id, Model model) {
        Optional<PersonModel> person = personRepository.findById(id);
        person.ifPresent(value -> model.addAttribute("cars", value.getCars()));
        return "persons/cars/index";
    }

    @GetMapping("/{personId}/cars/new")
    public String newCarForm(@PathVariable long personId, Model model) {
        Optional<PersonModel> person = personRepository.findById(personId);
        if (person.isPresent()) {
            CarModel car = new CarModel();
            car.setOwner(person.get());
            model.addAttribute("car", car);
            return "persons/cars/new";
        }
        return "redirect:/persons";
    }

    @PostMapping("/{personId}/cars")
    public String createCar(@PathVariable long personId, @Valid @ModelAttribute("car") CarModel car,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "persons/cars/new";
        }
        Optional<PersonModel> person = personRepository.findById(personId);
        person.ifPresent(value -> {
            car.setOwner(value);
            carRepository.save(car);
        });
        return "redirect:/persons";
    }

    @GetMapping("/{id}/modifyHouseAtPerson")
    public String showModifyHouseForm(@PathVariable long id, Model model) {
        Optional<PersonModel> person = personRepository.findById(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            model.addAttribute("houses", houseRepository.findAll());
            return "/persons/modifyHouseAtPerson";
        }
        return "redirect:/persons";
    }

    @PostMapping("/{id}/modifyHouseAtPerson")
    public String modifyHouseAtPerson(@PathVariable long id, @RequestParam("houseId") Long houseId) {
        Optional<PersonModel> person = personRepository.findById(id);
        Optional<HouseModel> house = houseRepository.findById(houseId);

        if (person.isPresent() && house.isPresent()) {
            PersonModel personModel = person.get();
            HouseModel houseModel = house.get();

            List<HouseModel> houses = personModel.getHouses();
            houses.add(houseModel);
            personModel.setHouses(houses);

            personRepository.save(personModel);
        }

        return "redirect:/persons/{id}/modifyHouseAtPerson";
    }

    @PostMapping("/{id}/removeHouse")
    public String removeHouseFromPerson(@PathVariable long id, @RequestParam("houseId") Long houseId) {
        Optional<PersonModel> person = personRepository.findById(id);
        Optional<HouseModel> house = houseRepository.findById(houseId);

        if (person.isPresent() && house.isPresent()) {
            PersonModel personModel = person.get();
            HouseModel houseModel = house.get();

            List<HouseModel> houses = personModel.getHouses();
            houses.remove(houseModel);
            personModel.setHouses(houses);

            personRepository.save(personModel);
        }

        return "redirect:/persons/{id}/modifyHouseAtPerson";
    }
}
