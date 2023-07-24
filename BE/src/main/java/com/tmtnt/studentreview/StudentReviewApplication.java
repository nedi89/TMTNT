package com.tmtnt.studentreview;

import com.tmtnt.studentreview.entity.Role;
import com.tmtnt.studentreview.entity.User;
import com.tmtnt.studentreview.repository.RoleRepository;
import com.tmtnt.studentreview.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class StudentReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentReviewApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent())
                return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            User admin = new User(1L, "", "", "admin", passwordEncoder.encode("password"), roles);
            userRepository.save(admin);


        };

    }

}