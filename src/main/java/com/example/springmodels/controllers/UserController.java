package com.example.springmodels.controllers;

import com.example.springmodels.ApiInterface;
import com.example.springmodels.models.ApplicationModel;
import com.example.springmodels.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final ApiInterface apiInterface;

    @Autowired
    public UserController(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @GetMapping("/profile")
    String getProfilePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = apiInterface.getUserAtUserName(auth.getName());
        List<ApplicationModel> applicationModel = apiInterface.findAllByPublisherId(userModel.getId());
        model.addAttribute("user", userModel);
        model.addAttribute("applications", applicationModel);
        model.addAttribute("salesStatisticsModel", apiInterface.findAllStatistic());
        return "user/profile";
    }
}
