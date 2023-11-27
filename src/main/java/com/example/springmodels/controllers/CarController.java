package com.example.springmodels.controllers;

import com.example.springmodels.models.CarModel;
import com.example.springmodels.repositories.CarRepository;
import com.example.springmodels.repositories.PersonRepository;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    public CarController(CarRepository carRepository, PersonRepository personRepository) {
        this.carRepository = carRepository;
        this.personRepository = personRepository;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "cars/index";
    }

    @GetMapping("/new")
    public String newCarForm(Model model) {
        model.addAttribute("car", new CarModel());
        model.addAttribute("owners", personRepository.findAll());
        return "cars/new";
    }

    @PostMapping()
    public String createCar(@Valid @ModelAttribute("car") CarModel car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cars/new";
        }
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/{id}/edit")
    public String editCarForm(@PathVariable Long id, Model model) {
        CarModel car = carRepository.findById(id).orElse(null);
        if (car != null) {
            model.addAttribute("car", car);
            model.addAttribute("owners", personRepository.findAll());
            return "cars/edit";
        }
        return "redirect:/cars";
    }

    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable Long id, @Valid @ModelAttribute("car") CarModel car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cars/edit";
        }
        carRepository.save(car);
        return "redirect:/cars";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carRepository.deleteById(id);
        return "redirect:/cars";
    }

    @GetMapping("/{id}")
    public String showCar(@PathVariable Long id, Model model) {
        CarModel car = carRepository.findById(id).orElse(null);
        if (car != null) {
            model.addAttribute("car", car);
            return "cars/show";
        }
        return "redirect:/cars";
    }

    @GetMapping("/search")
    public String searchByMake(@RequestParam("make") String make, Model model) {
        List<CarModel> carByMake = carRepository.findByMake(make);
        model.addAttribute("cars", carByMake);
        return "cars/search_results";
    }
}
