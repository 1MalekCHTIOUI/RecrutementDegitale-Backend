package com.tekup.recrutement.services;

import com.tekup.recrutement.dto.SignupDTO;
import com.tekup.recrutement.dto.UserDTO;

public interface UserService {
    public UserDTO createUser(SignupDTO signupDTO);
}
