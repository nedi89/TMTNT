package com.tmtnt.studentreview.service;

import com.tmtnt.studentreview.dto.LoginResponseDTO;
import com.tmtnt.studentreview.entity.Role;
import com.tmtnt.studentreview.entity.User;
import com.tmtnt.studentreview.repository.RoleRepository;
import com.tmtnt.studentreview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        return userRepository.save(new User(1L, "", "", username, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            String token = tokenService.generateJWT(authentication);
            return new LoginResponseDTO(userRepository.findUserByEmail(email).get(), token);
        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }

    }
}
