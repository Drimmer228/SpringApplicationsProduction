package com.example.springmodels.controllers;

import com.example.springmodels.models.HouseModel;
import com.example.springmodels.repositories.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/houses")
@PreAuthorize("hasAnyAuthority('MODER', 'USER')")
public class HouseController  extends CrudController<HouseModel, Long> {
    private final HouseRepository houseRepository;

    @Autowired
    public HouseController(HouseRepository houseRepository) {
        super(houseRepository, "house");
        this.houseRepository = houseRepository;
    }

    @GetMapping("/search")
    public String searchByAddress(@RequestParam("address") String address, Model model) {
        List<HouseModel> houseByAddress = houseRepository.findByAddress(address);
        model.addAttribute("houses", houseByAddress);
        return "houses/search_results";
    }
}
