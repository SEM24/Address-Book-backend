package com.chisoftware.user.service;

import com.chisoftware.user.model.dto.RegistrationDTO;
import com.chisoftware.user.model.dto.Token;
import com.chisoftware.user.model.entity.User;

public interface UserService {
    Token authentication(RegistrationDTO request);

    Token signIn(String username);

    User findUserByUsername(String name);

    boolean isNotCurrentUser(User user);
}
