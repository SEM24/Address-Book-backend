package com.chisoftware.user.mapper;

import com.chisoftware.user.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.security.core.userdetails.User.withUsername;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    default UserDetails toUserDetails(User user) {
        return withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(SimpleGrantedAuthority::new).toList())
                .build();
    }
}
