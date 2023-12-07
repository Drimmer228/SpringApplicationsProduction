package com.example.springmodels.controllers;

import com.example.springmodels.ApiInterface;
import com.example.springmodels.EmailService;
import com.example.springmodels.models.RoleModel;
import com.example.springmodels.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;

import java.time.DateTimeException;
import java.time.LocalDate;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final ApiInterface apiInterface;

    @Autowired
    public PaymentController(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @Autowired
    private EmailService emailService;

    @PostMapping("/processPayment")
    public ResponseEntity<String> processPayment() {
        RoleModel roleModel = apiInterface.getRoleAtName("Admin");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = apiInterface.getUserAtUserName(auth.getName());
        if(userModel.getBalance()-100>=0){
            userModel.setBalance(userModel.getBalance()-100);
            userModel.setPainFee(true);
            UserModel adminModel = apiInterface.findByRole(roleModel);
            adminModel.setBalance(adminModel.getBalance()+100);
            apiInterface.saveUser(userModel);
            apiInterface.saveUser(adminModel);
            return ResponseEntity.ok("Платеж успешно обработан");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обработке платежа");
        }
    }

    @GetMapping()
    private String getIndexPage(Model model,
                                @RequestParam("topUpAmount") double amount,
                                @RequestParam("action") String action){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = apiInterface.getUserAtUserName(auth.getName());

        model.addAttribute("action", action);
        model.addAttribute("amount", amount);
        model.addAttribute("performer", userModel);
        return "payment/index";
    }

    @PostMapping("/validData")
    private String addUserBalance(@RequestParam("expirationDate") String expirationDate,
                                  @RequestParam("amount") double amount,
                                  @RequestParam("action") String action,
                                  Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = apiInterface.getUserAtUserName(auth.getName());

        String[] parts = expirationDate.split("/");
        if (parts.length != 2) {
            model.addAttribute("error", "Некорректный формат даты");
            model.addAttribute("action", action);
            model.addAttribute("amount", amount);
            model.addAttribute("performer", userModel);
            return "payment/index";
        }

        try {
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            LocalDate currentDate = LocalDate.now();

            LocalDate inputDate = LocalDate.of(2000 + year, month, 1);

            if (currentDate.isAfter(inputDate)) {
                model.addAttribute("error", "Ваша карта просрочена");
                return "payment/index";
            }
            try {
                emailService.sendEmail(userModel.getEmail(), "Электронный чек об оплате", action + " - успешно\nСумма: " + amount + "\nС уважением, DRcorp");
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            userModel.setBalance(userModel.getBalance() + amount);
            apiInterface.saveUser(userModel);

            return "redirect:/user/profile";
        } catch (NumberFormatException | DateTimeException e) {
            model.addAttribute("error", "Карта недействительна");
            model.addAttribute("action", action);
            model.addAttribute("amount", amount);
            model.addAttribute("performer", userModel);
            return "payment/index";
        }
    }


}
