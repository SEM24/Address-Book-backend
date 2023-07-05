package com.chisoftware.user.service;

import com.chisoftware.user.model.dto.RegistrationDTO;
import com.chisoftware.user.model.dto.Token;

public interface UserService {
    Token authentication(RegistrationDTO request);

    Token signIn(String username);
}
