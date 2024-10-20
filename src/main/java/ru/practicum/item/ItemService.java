package ru.practicum.item;

import java.util.List;

public interface ItemService {
    Item addItem(Item item);
    Item updateItem(Long itemId, Item item);
    Item getItem(Long itemId);
    List<Item> getAllItems();
}