package com.service;

import com.entity.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    @Bean
    private PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }



    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        user.setPassword(getEncoder().encode(user.getPassword()));
        userRepository.save(user);
        user.setRootdir(String.valueOf(user.getId()));
        userRepository.save(user);

    }

    public User getUserByEmailId(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> login(String email, String password) {
        //email = getEncoder().encode(email);
        User user =  userRepository.findByEmail(email);
        List<User> lst = new ArrayList();
        System.out.println(password + "   "+user.getPassword());
        if(getEncoder().matches(password,user.getPassword())){
            System.out.println("matched");
            lst.add(user);
        }
        return lst;
    }
}