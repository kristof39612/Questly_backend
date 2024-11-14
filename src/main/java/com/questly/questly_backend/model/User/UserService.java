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

    public boolean userEmailExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean userNameExists(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    public String getEmailFromSecurityContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            return ((User)principal).getEmail();
        }
        return principal.toString();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getLoggedInUser(){
        return userRepository.findByEmail(getEmailFromSecurityContext()).orElseThrow();
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Long getLoggedInUserId(){
        return userRepository.findByEmail(getEmailFromSecurityContext()).orElseThrow().getId();
    }

    public User getClientById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public void deletedCoach(){}

    public void setTask(Long taskPointId) {
        User user = getLoggedInUser();
        user.setCurrentTaskPointId(taskPointId);
        userRepository.save(user);
    }
}
