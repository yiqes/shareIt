package ru.practicum.item;

import java.util.List;

public interface ItemRepository {
    List<Item> findAll();
    Item findById(Long id);
    Item save(Item item);
    Item updateItem(Item item, Long id);
}