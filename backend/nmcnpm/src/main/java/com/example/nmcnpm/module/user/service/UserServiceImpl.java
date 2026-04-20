package com.example.nmcnpm.module.user.service;

import com.example.nmcnpm.module.user.entity.User;
import com.example.nmcnpm.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserServiceImpl – implementation của IUserService.
 * Cũng implement UserDetailsService để Spring Security có thể load user.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByIdentifier(String identifier) {
        return userRepository.findByIdentifier(identifier);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Spring Security gọi method này khi xác thực.
     * Tìm theo email HOẶC username (FR-01).
     */
    @Override
    public UserDetails loadUserByUsername(String identifier)
            throws UsernameNotFoundException {
        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy user: " + identifier));
    }
}
