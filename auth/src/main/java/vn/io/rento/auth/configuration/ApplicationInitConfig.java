package vn.io.rento.auth.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.io.rento.auth.entity.Role;
import vn.io.rento.auth.entity.User;
import vn.io.rento.auth.repository.PermissionRepository;
import vn.io.rento.auth.repository.RoleRepository;
import vn.io.rento.auth.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    private final String roleName = "ADMIN";

    private final String username = "admin";

    private final String password = "admin";

    private final String email = "admin@admin.com";

    @Bean
//    @ConditionalOnProperty(value = "app.testMode", havingValue = "false")
    ApplicationRunner applicationRunner(
            UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            if (userRepository.findById(username).isEmpty()) {
                Role role = new Role(roleName, "admin role", new HashSet<>());
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                User user = User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .roles(roles)
                        .enabled(true)
                        .build();
                roleRepository.save(role);
                userRepository.save(user);
                log.info("First run app with Administrator account: admin");
            }
        };
    }
}
