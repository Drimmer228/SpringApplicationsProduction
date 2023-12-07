package com.example.springmodels.controllers;

import com.example.springmodels.ApiInterface;
import com.example.springmodels.models.ApplicationModel;
import com.example.springmodels.models.ApplicationStatusModel;
import com.example.springmodels.models.NotificationsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/moder")
public class ModerController {
    private final ApiInterface apiInterface;

    @Autowired
    public ModerController(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }
    @GetMapping("/usersApplications")
    private String getPageWithUserApplications(Model model){
        List<String> uniqueUserApp = apiInterface.findDistinctPublisherIds();
        List<ApplicationModel> applicationModelList = apiInterface.findAllByPublisherRoleRoleName("User");
        model.addAttribute("userNames", uniqueUserApp);
        model.addAttribute("applications", applicationModelList);
        return "application/index";
    }

    @GetMapping("/application/show/{id}")
    private String changeStatusApplicationAndGetPageInfo(@PathVariable Long id, Model model){
        ApplicationModel applicationModel = apiInterface.getApplicationAtId(id);
        if(applicationModel.getStatus().getName().equals("Отправлено на рассмотрение")){
            ApplicationStatusModel applicationStatusModel = apiInterface.getStatusAtName("На рассмотрении");
            applicationModel.setStatus(applicationStatusModel);
            apiInterface.saveApplication(applicationModel);

            NotificationsModel notificationsModel = new NotificationsModel();
            notificationsModel.setRead(false);
            notificationsModel.setMessage("Заявка № " + applicationModel.getId() + " находится на рассмотрении");
            notificationsModel.setUser(applicationModel.getPublisher());
            apiInterface.saveNotification(notificationsModel);
        }
        model.addAttribute("application_model", applicationModel);
        return "redirect:/application/" + id;
    }
}
