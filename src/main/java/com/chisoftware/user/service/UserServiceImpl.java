package com.chisoftware.user.service;

import com.chisoftware.user.UserRepository;
import com.chisoftware.user.handler.exception.UserAlreadyExistsException;
import com.chisoftware.user.handler.exception.UserNotFoundException;
import com.chisoftware.user.model.dto.RegistrationDTO;
import com.chisoftware.user.model.dto.Token;
import com.chisoftware.user.model.entity.Role;
import com.chisoftware.user.model.entity.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private JwtEncoder jwtEncoder;
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Override
    public Token authentication(RegistrationDTO request) {
        User user = createUser(request);
        userRepo.save(user);
        return generateJwtToken(request.username(), getRoles(user));
    }

    @Override
    public Token signIn(String username) {
        User user = findUserByUsername(username);
        return generateJwtToken(username, getRoles(user));
    }
    @Override
    public User findUserByUsername(String name) {
        return userRepo.findByUsername(name)
                .orElseThrow(UserNotFoundException::new);
    }

    private User createUser(RegistrationDTO registrationDTO) {
        if (userRepo.existsByUsername(registrationDTO.username())) {
            throw new UserAlreadyExistsException();
        }
        return buildUser(registrationDTO);
    }

    private User buildUser(RegistrationDTO request) {
        return User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                //FIXME Hardcoding USER role due to simplifying the code
                .roles(Collections.singleton(Role.USER.getAuthority()))
                .build();
    }

    private Token generateJwtToken(String subject, String role) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("CHI-Software")
                .issuedAt(now)
                .expiresAt(now.plus(60, ChronoUnit.MINUTES))
                .subject(subject)
                .claim("scope", role)
                .build();
        return Token.builder().token(
                jwtEncoder.encode(JwtEncoderParameters.from(claims))
                        .getTokenValue()).build();
    }

    private String getRoles(User saveUser) {
        Collection<? extends GrantedAuthority> auth =
                saveUser.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return auth.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
    }
}
