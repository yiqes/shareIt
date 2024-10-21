package ru.practicum.user;

import org.springframework.stereotype.Component;
import ru.practicum.errors.NotFoundException;
import ru.practicum.errors.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User save(User user) {
        isUserValid(user);
        user.setId(getId());
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        users.add(user);
        return user;
    }

    @Override
    public User updateUserById(User user, Long id) {
        Optional<User> existedUser  = users.stream()
                .filter(u -> u.getId().equals(id)) // Ищем по переданному id
                .findFirst();

        if (existedUser .isPresent()) {
            // Проверка на существование email
            String email = user.getEmail();
            if (email != null &&
                    users.stream().anyMatch(u -> email.equals(u.getEmail()) && !u.getId().equals(id))) {
                throw new ValidationException("Пользователь с таким email уже существует");
            }
            User userToUpdate = existedUser .get();
            if (user.getName() != null) {
                userToUpdate.setName(user.getName());
            }
            if (email != null) {
                userToUpdate.setEmail(email);
            }
            return userToUpdate;
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }
    @Override
    public User findById(Long id) {
        Optional<User> existedUser  = users.stream()
                .filter(u -> u.getId().equals(id)) // Ищем по переданному id
                .findFirst();
        if (existedUser.isPresent()) {
            return existedUser.get();
        } else {
            throw new NotFoundException("Пользователь не найден");
        }

    }

    private long getId() {
        long lastId = users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }


    @Override
    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
    private void isUserValid(User user) {
        String email = user.getEmail();
        if (email == null || !(email.contains("@"))) {
            throw new ValidationException("Неверно задан email пользователя");
        }
        // Проверка на существование email
        if (users.stream().anyMatch(u -> email.equals(u.getEmail()) && !u.getId().equals(user.getId()))) {
            throw new ValidationException("Пользователь с таким email уже существует");
        }
    }
}
