package com.chisoftware.user;


import com.chisoftware.user.handler.exception.UserAlreadyExistsException;
import com.chisoftware.user.model.dto.RegistrationDTO;
import com.chisoftware.user.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepo;
    @InjectMocks
    private UserServiceImpl userService;
    @Test
    @DisplayName("User Authentication - User Already Exists")
    void given_RegistrationDTO_When_Authentication_Then_ThrowUserAlreadyExistsException() {
        //Mock the registration DTO
        RegistrationDTO registrationDTO = new RegistrationDTO("testUsername", "123456");
        //Mock the user repository
        when(userRepo.existsByUsername(registrationDTO.username())).thenReturn(true);

        //Perform the authentication and assert that it throws UserAlreadyExistsException
        assertThrows(UserAlreadyExistsException.class, () -> userService.authentication(registrationDTO));

        //Verify the interaction
        verify(userRepo).existsByUsername(registrationDTO.username());
    }
}
