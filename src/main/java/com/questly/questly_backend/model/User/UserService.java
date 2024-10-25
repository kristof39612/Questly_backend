package com.questly.questly_backend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean userExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public String getEmailFromSecurityContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            return ((User)principal).getEmail();
        }
        return principal.toString();
    }


    public User getLoggedInUser(){
        return userRepository.findByEmail(getEmailFromSecurityContext()).orElseThrow();
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Integer getLoggedInUserId(){
        return userRepository.findByEmail(getEmailFromSecurityContext()).orElseThrow().getId();
    }

    public User getClientById(Integer id){
        return userRepository.findById(id).orElseThrow();
    }




    public void deletedCoach(){}




}
