package ru.practicum.item;

import org.springframework.stereotype.Repository;
import ru.practicum.errors.NotFoundException;
import ru.practicum.errors.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final List<Item> items = new ArrayList<>();

    @Override
    public List<Item> findAll() {
        return items;
    }

    @Override
    public Item findById(Long id) {
        Optional<Item> existingItem = items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
        if (existingItem.isPresent()) {
            return existingItem.get();
        } else {
            throw new NotFoundException("Предмет не найден");
        }
    }

    @Override
    public Item save(Item item) {
        if (item.getAvailable() == null) {
            throw new ValidationException("Статус не известен");
        }
        if (item.getOwnerId() == null) {
            throw new NotFoundException("Владелец не найден");
        }
        item.setId(item.getId());
        item.setName(item.getName());
        item.setDescription(item.getDescription());
        item.setAvailable(item.getAvailable());
        item.setOwnerId(item.getOwnerId());
        items.add(item);
        return item;
    }
    @Override
    public Item updateItem(Item item, Long id) {
        Optional<Item> existingItem = items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
        if (existingItem.isPresent()) {
            Item itemToUpdate = existingItem.get();
            itemToUpdate.setName(item.getName());
            itemToUpdate.setDescription(item.getDescription());
            itemToUpdate.setAvailable(item.getAvailable());
            itemToUpdate.setOwnerId(item.getOwnerId());
            return itemToUpdate;
        } else {
            throw new NotFoundException("Предмет не найден");
        }
    }
}