package com.example.springmodels.controllers;

import com.example.springmodels.ApiInterface;
import com.example.springmodels.models.RoleModel;
import com.example.springmodels.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class RegistrationController {
    private final ApiInterface apiInterface;

    @Autowired
    public RegistrationController(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/registration")
    private String RegView()
    {
        return "regis";
    }
    @PostMapping("/registration")
    private String Reg(UserModel user, Model model) {
        UserModel userFromDb = apiInterface.getUserAtUserName(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "regis";
        }

        RoleModel userRole = apiInterface.getRoleAtName("User");
        user.setRole(userRole);
        user.setActive(true);
        LocalDate registrationDate = LocalDate.now();
        user.setRegistrationDate(registrationDate);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setTotalSpent(0);
        user.setPainFee(false);
        user.setPersonalDiscount(0);
        user.setBalance(0);
        apiInterface.saveUser(user);
        return "redirect:/login";
    }
}
