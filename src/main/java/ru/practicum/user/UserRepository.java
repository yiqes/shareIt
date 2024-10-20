package ru.practicum.user;

import java.util.List;

interface UserRepository {
    List<User> findAll();
    User save(User user);
    User findById(Long id);
    User updateUserById(User user, Long id);
/*    User updateUser(User user);*/
    void deleteUser(Long id);
}