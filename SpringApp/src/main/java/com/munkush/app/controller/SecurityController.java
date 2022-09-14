package com.munkush.app.controller;

import com.munkush.app.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SecurityController {

    private final UsersServiceImpl usersService;
    public static String exception = null;

    @Autowired
    public SecurityController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("message",exception);
        return "login";
    }


    @GetMapping("/")
    public String mainPage(){
        return "main";
    }



}
