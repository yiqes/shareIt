package ru.practicum.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUserById(User user, Long id);
    User findById(Long id);
    void deleteUser(Long id);
}