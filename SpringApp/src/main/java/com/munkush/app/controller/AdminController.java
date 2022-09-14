package com.munkush.app.controller;

import com.munkush.app.entity.Role;
import com.munkush.app.entity.Users;
import com.munkush.app.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/for-admins")
public class AdminController {

    private final UsersServiceImpl usersService;

    @Autowired
    public AdminController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String forAdminsPage(Model model){
        model.addAttribute("users", usersService.findAll());
        return "admin/admin";
    }

    @GetMapping("/{id}")
    public String getOneUser(@PathVariable("id") int id,Model model){

        Users users = usersService.findById(id);
        model.addAttribute("singleUser",users);

        return "admin/show";
    }
    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id,Model model){

        Set<Role> roles = usersService.findById(id).getRole();

        model.addAttribute("userEdit", usersService.findById(id));

        model.addAttribute("userBool", usersService.isUser(roles));
        model.addAttribute("adminBool", usersService.isAdmin(roles));


        return "admin/edit";
    }

    @PostMapping
    public String saveEditedUser(@RequestParam(value = "userBool",required = false) boolean ub,
                                 @RequestParam(value = "adminBool",required = false) boolean ab,
                                 @ModelAttribute("editedUser") Users users){

        Set<Role> role = new HashSet<>();

        if(ub&&!usersService.isUser(users.getRole())) {
             role.add(Role.USER);
             users.setRole(role);
        }

        if(ab&&!usersService.isAdmin(users.getRole())) {
            role.add(Role.ADMIN);
            users.setRole(role);
        }


        usersService.addUser(users);

        return "redirect:/for-admins";
    }

    @GetMapping("{id}/delete")
    public String deleteUser(@PathVariable("id") int id){
        usersService.delete(id);
        return "redirect:/for-admins";
    }


}
