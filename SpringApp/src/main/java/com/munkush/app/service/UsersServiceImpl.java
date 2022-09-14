package com.munkush.app.service;

import com.munkush.app.controller.SecurityController;
import com.munkush.app.entity.Role;
import com.munkush.app.entity.Users;
import com.munkush.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UsersServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    MailSender mailSender;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public boolean addUser(Users users){
        Users userFromDb = usersRepository.findByUsername(users.getUsername());
        if(userFromDb != null) {
            return false;
        }

        users.setActive(true);
        users.setRole(Collections.singleton(Role.USER));
        users.setActivationCode(UUID.randomUUID().toString());

        usersRepository.save(users);

        if(!StringUtils.isEmpty(users.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Gheater. Please visit next link: " +
                            "http://localhost:/8080/activate/%s",
                    users.getUsername(),
                    users.getActivationCode()
            );
            mailSender.send(users.getEmail(), "Activation code", message);
        }


        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsername(username);

        if(users == null){
            SecurityController.exception = "Account not registered, please register";
            return null;
        }

        return users;
    }

    public List<Users> findAll(){
        return usersRepository.findAll();
    }

    public Users findById(int id){
        Users users = usersRepository.findById(id).get();
       if(users!=null)
           return users;
       else return null;
    }

    public boolean isAdmin(Set<Role> role){
        if(role==null)
            return false;
        ArrayList<Role> list = new ArrayList<>();
        list.addAll(role);

        for (Role r : list
             ) {
            if(r.getAuthority()=="ADMIN")
                return true;
        }
        return false;
    }

    public boolean isUser(Set<Role> role){
        if(role==null)
            return false;
        ArrayList<Role> list = new ArrayList<>();
        list.addAll(role);

        for (Role r : list
        ) {
            if(r.getAuthority()=="USER")
                return true;
        }
        return false;
    }
    public void delete(int id){
        usersRepository.deleteById(id);
    }


    public boolean activateUser(String code) {
        Users users =usersRepository.findByActivationCode(code);

        if(users == null)
            return false;

        users.setActivationCode(null);

        usersRepository.save(users);

        return true;

    }
}
