package com.example.hotel_booking_be_v1.service;

import com.example.hotel_booking_be_v1.exception.UserAlreadyExistsException;
import com.example.hotel_booking_be_v1.model.Role;
import com.example.hotel_booking_be_v1.model.User;
import com.example.hotel_booking_be_v1.repository.RoleRepository;
import com.example.hotel_booking_be_v1.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public User registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail()+"already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);



    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if (theUser != null){
            userRepository.deleteByEmail(email);
        }
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found "));


    }

    @Override
    public User getUserById (Long id){
        return userRepository.findById(id).orElse(null);
    }
}
