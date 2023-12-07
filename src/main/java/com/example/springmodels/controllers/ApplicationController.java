package com.example.springmodels.controllers;

import com.example.springmodels.ApiInterface;
import com.example.springmodels.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/application")
public class ApplicationController {

    private final ApiInterface apiInterface;

    @Autowired
    public ApplicationController(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @GetMapping("/new")
    private String getCreateApplicationPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("application_model", new ApplicationModel());
        model.addAttribute("categories", apiInterface.getAllCategoryModels());
        model.addAttribute("user", apiInterface.getUserAtUserName(auth.getName()));
        return "application/new";
    }

    @GetMapping("/{id}")
    private String getInfoApplicationPage(@PathVariable Long id, Model model){
        ApplicationModel applicationModel = apiInterface.getApplicationAtId(id);
        model.addAttribute("application_model", applicationModel);
        model.addAttribute("categories", apiInterface.getAllCategoryModels());
        return "application/info";
    }

    @PostMapping("/new")
    private String createNewApplication(@ModelAttribute("application_model") ApplicationModel applicationModel,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("image") MultipartFile image,
                                        @RequestParam("action") String action,
                                        Model model){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel userModel = apiInterface.getUserAtUserName(auth.getName());
            ApplicationStatusModel statusModel = apiInterface.getStatusAtName("Отправлено на рассмотрение");
            applicationModel.setPublisher(userModel);
            applicationModel.setImageApp(image.getBytes());
            applicationModel.setStatus(statusModel);
            applicationModel.setNameExecutableFile(file.getOriginalFilename());
            applicationModel.setExecutableFile(file.getBytes());

            if(action.equals("saveApp")){
                statusModel = apiInterface.getStatusAtName("Одобрено");
                applicationModel.setStatus(statusModel);
                applicationModel.setActive(false);
            }else if(action.equals("submitApp")){
                statusModel = apiInterface.getStatusAtName("Одобрено");
                applicationModel.setStatus(statusModel);
                if(userModel.getPainFee()) applicationModel.setActive(true);
                else{
                    auth = SecurityContextHolder.getContext().getAuthentication();
                    userModel = apiInterface.getUserAtUserName(auth.getName());

                    model.addAttribute("application_model", new ApplicationModel());
                    model.addAttribute("categories", apiInterface.getAllCategoryModels());
                    model.addAttribute("user", userModel);
                    model.addAttribute("errorSubmit", "Нельзя опубликовать приложение до оплаты взноса");
                    return "application/new";
                }
            }

            apiInterface.saveApplication(applicationModel);

            return "redirect:/user/profile";
        }catch (Exception e){
            String message = "Ошибка отправки заявки. Попробуйте позже";
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel userModel = apiInterface.getUserAtUserName(auth.getName());

            model.addAttribute("application_model", new ApplicationModel());
            model.addAttribute("categories", apiInterface.getAllCategoryModels());
            model.addAttribute("user", userModel);
            model.addAttribute("error", message);
            return "application/new";
        }
    }

    @PostMapping("/updateOrDelete/{id}")
    private String updateApplication(@PathVariable Long id, @Valid @ModelAttribute("application_model") ApplicationModel applicationModel,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("image") MultipartFile image,
                                        @RequestParam("action") String action,
                                        Model model){
        try {
            ApplicationModel tempApplication = apiInterface.getApplicationAtId(id);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel userModel = apiInterface.getUserAtUserName(auth.getName());
            ApplicationStatusModel statusModel = new ApplicationStatusModel();
            switch (action){
                case "save":
                    if(tempApplication.getActive() == null) statusModel = apiInterface.getStatusAtName("Отправлено на рассмотрение");
                    break;
                case "delete":
                    statusModel = apiInterface.getStatusAtName("Отозвана");
                    break;
                case "confirmApplication":
                    statusModel = apiInterface.getStatusAtName("Одобрено");
                    userModel = apiInterface.getUserAtId(applicationModel.getPublisher().getId());
                    userModel.setRole(apiInterface.getRoleAtName("Developer"));
                    applicationModel.setActive(false);
                    apiInterface.saveUser(userModel);
                    break;
                case "rejectApplication":
                    statusModel = apiInterface.getStatusAtName("Отклонено");
                    userModel = apiInterface.getUserAtId(applicationModel.getPublisher().getId());
                    break;
                case "uploadInCatalog":
                    applicationModel.setActive(true);
                    break;
                case "rejectFromCatalog":
                    applicationModel.setActive(false);
                    break;
            }

            if(applicationModel.getActive() == null) applicationModel.setActive(tempApplication.getActive());

            applicationModel.setPublisher(userModel);
            if(!file.isEmpty()) applicationModel.setExecutableFile(file.getBytes());
            else{
                applicationModel.setExecutableFile(tempApplication.getExecutableFile());
            }

            if(!image.isEmpty()) applicationModel.setImageApp(image.getBytes());
            else{
                applicationModel.setImageApp(tempApplication.getImageApp());
            }

            if(statusModel.getName()!=null){
                applicationModel.setStatus(statusModel);
            }else{
                applicationModel.setStatus(tempApplication.getStatus());
            }
            apiInterface.saveApplication(applicationModel);
            return "redirect:/user/profile";
        }catch (Exception e){
            String message = "Ошибка. Попробуйте позже";
            model.addAttribute("error", message);
            return "application/info";
        }
    }
}
