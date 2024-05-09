package com.tekup.recrutement.services;

import com.tekup.recrutement.dto.SignupDTO;
import com.tekup.recrutement.dto.UserDTO;
import com.tekup.recrutement.entities.User;

public interface UserService {
    public UserDTO createUser(SignupDTO signupDTO);

    public User getUser(Long id);
}
