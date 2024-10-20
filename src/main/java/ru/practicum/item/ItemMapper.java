/*
package ru.practicum.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ItemMapper {

    // Метод для преобразования Item в ItemDto
    public static ItemDto mapToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable(), item.getOwnerId());
    }

    // Метод для преобразования списка Item в список ItemDto
    public static List<ItemDto> mapToItemDto(Iterable<Item> items) {
        List<ItemDto> result = new ArrayList<>();

        for (Item item : items) {
            result.add(mapToItemDto(item));
        }

        return result;
    }

    // Метод для создания нового Item из ItemDto
    public static Item mapToNewItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwnerId(itemDto.getOwnerId());
        return item;
    }
}*/
package ru.practicum.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {

    // Метод для преобразования Item в ItemDto
    public static ItemDto mapToItemDto(Item item) {
        if (item == null) {
            return null; // Обработка случая, когда item равен null
        }
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable(), item.getOwnerId());
    }

    // Метод для преобразования списка Item в список ItemDto
    public static List<ItemDto> mapToItemDto(Iterable<Item> items) {
        List<ItemDto> result = new ArrayList<>();
        if (items != null) {
            for (Item item : items) {
                result.add(mapToItemDto(item));
            }
        }
        return result;
    }

    // Метод для создания нового Item из ItemDto
    public static Item mapToNewItem(ItemDto itemDto) {
        if (itemDto == null) {
            return null; // Обработка случая, когда itemDto равен null
        }
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        // ownerId не устанавливается здесь, так как он будет установлен в контроллере
        return item;
    }
}