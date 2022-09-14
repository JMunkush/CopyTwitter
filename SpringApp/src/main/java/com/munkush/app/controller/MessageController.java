package com.munkush.app.controller;

import com.munkush.app.entity.Message;
import com.munkush.app.entity.Users;
import com.munkush.app.service.MessageServiceImpl;
import com.munkush.app.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    private final MessageServiceImpl messageService;
    private final UsersServiceImpl usersService;

    private List<Message> listFromSearch = new ArrayList<>();
    private String filter;


    @Autowired
    public MessageController(MessageServiceImpl messageService, UsersServiceImpl usersService) {
        this.messageService = messageService;
        this.usersService = usersService;
    }


    @GetMapping("/index")
    public String indexPage(@AuthenticationPrincipal Users users,Model model){

        if(listFromSearch.isEmpty()||listFromSearch.size()>1)
            listFromSearch = messageService.getAll();

        model.addAttribute("messages", listFromSearch);
        model.addAttribute("messageObject", new Message());
        model.addAttribute("filter", filter);
        model.addAttribute("isAdmin", usersService.isAdmin(users.getRole()));
        model.addAttribute("username", users.getUsername());

        return "index";
    }

    @PostMapping("/index")
    public String saveMessage(@AuthenticationPrincipal Users users, @ModelAttribute("newMessage") Message message){
        message.setAuthor(users);
        messageService.save(message);
        return "redirect:/index";

    }

    @PostMapping("index/search")
    public String searchMessage(@RequestParam(value = "filter", required = false) String s,Model model){

        if(s!=null) {
            filter = s;
        }

        listFromSearch.clear();
        listFromSearch = messageService.findByTag(s);



        return "redirect:/index";

        }


    }


