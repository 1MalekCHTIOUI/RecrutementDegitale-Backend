package com.tekup.recrutement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tekup.recrutement.dao.UserRepository;
import com.tekup.recrutement.dto.SignupDTO;
import com.tekup.recrutement.dto.UserDTO;
import com.tekup.recrutement.entities.User;
import com.tekup.recrutement.enums.UserRole;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByUserRole(UserRole.SupAdmin);
        if (adminAccount == null) {
            User user = new User();
            user.setUserRole(UserRole.SupAdmin);
            user.setEmail("admin@gmail.com");
            user.setName("SupAdmin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }

    }

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());

        user.setUserRole(UserRole.valueOf(signupDTO.getUserRole().toString()));

        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));

        User createdUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        userDTO.setPassword(createdUser.getPassword());
        userDTO.setUserRole(createdUser.getUserRole());
        return userDTO;

    }
}
