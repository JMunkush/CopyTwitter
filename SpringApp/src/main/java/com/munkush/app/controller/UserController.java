package com.munkush.app.controller;

import com.munkush.app.entity.Users;
import com.munkush.app.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    @Autowired
    private UsersServiceImpl usersService;

    @GetMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("user", new Users());
        return "registration";
    }

    @PostMapping("/registration")
    public String newUser(@ModelAttribute("user") Users users, Model model) {

        if (!usersService.addUser(users)){
            model.addAttribute("message", "User Exists !");
            return "registration";
        }

        return "redirect:/login";

    }
    @PostMapping("/activation/{code}")
    public String activationPage(Model model, @PathVariable String code){

        boolean isActivated = usersService.activateUser(code);

        if(isActivated)
            model.addAttribute("message", "User Succesfully Activated !");
        if(!isActivated)
            model.addAttribute("message","Activation code isnt found :(");

        return "login";
    }



}

