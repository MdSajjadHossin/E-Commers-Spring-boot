package com.springboot.service.auth;

import com.springboot.dto.SignupRequest;
import com.springboot.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
