package com.example.Appointment.service;

import com.example.Appointment.entity.User;
import com.example.Appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public List<User> getUsers(List<User> currentList) {
        List<User> actualList = new ArrayList<>();
        for (User user: currentList) {
            User tmpUser = new User();
            tmpUser.setEmail(user.getEmail());
            tmpUser.setFirstName(user.getFirstName());
            tmpUser.setLastName(user.getLastName());
            tmpUser.setRole_id(user.getRole_id());
            tmpUser.setId(user.getId());
            actualList.add(tmpUser);
        }
        return actualList;
    }
}
