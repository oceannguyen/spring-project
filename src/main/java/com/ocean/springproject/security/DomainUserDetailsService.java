package com.ocean.springproject.security;

import com.ocean.springproject.domain.User;
import com.ocean.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailService")
@Primary
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optionalUser = this.userRepository.findByEmail(email);

        if (!optionalUser.isPresent()) {
           throw new UsernameNotFoundException("User " + email + " was not found");
        }

        User user = optionalUser.get();
        return this.createSpringSecurityUser(user);
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        if (!user.getIsEmailConfirm()) {
            throw new UserNotActivatedException("User " + user.getEmail() + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getUserRoles()
                                            .stream()
                                            .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getRole().getName()))
                                            .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(), grantedAuthorities);
    }
}
