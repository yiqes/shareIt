package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User updateUserById(User user, Long id) {
        return repository.updateUserById(user, id);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id);
    }


    @Override
    public void deleteUser(Long id) {
        repository.deleteUser(id);
    }

}