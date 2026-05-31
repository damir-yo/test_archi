package com.peachub.app.service;

import com.peachub.app.security.CustomUserDetails;
import com.peachub.app.entity.User;
import com.peachub.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("Попытка аутентификации пользователя {}", email);

        User user = userRepository.findByEmail(email).orElseThrow(() -> {
                    log.warn("Неудачная попытка входа: пользователь {} не найден", email);

                    return new UsernameNotFoundException("User not found: " + email);
                });

        log.info("Пользователь {} успешно найден для аутентификации", email);
        return new CustomUserDetails(user);
    }
}
