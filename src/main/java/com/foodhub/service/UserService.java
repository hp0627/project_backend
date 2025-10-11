package com.foodhub.service;

import com.foodhub.model.User;
import com.foodhub.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }
    public Optional<User> findByEmail(String email) { return repo.findByEmail(email); }
    public User save(User user) { return repo.save(user); }
}
