package ru.practicum.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class User {
    private Long id;
    private String email;
    private String name;
}