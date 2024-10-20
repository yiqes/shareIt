/*
package ru.practicum.item;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public Item addItem(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody Item item) {
        return itemService.addItem(item);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@PathVariable Long itemId, @RequestBody Item item) {
        return itemService.updateItem(itemId, item);
    }

    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<Item> getAllItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllItems();
    }

    //@GetMapping("/search")
    //public List<Item> searchItems(@RequestParam String text) {
      //  return itemService.searchItems(text);
    //}
}*/
package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final List<Item> items = new ArrayList<>();
    private final UserService userService;
    private long currentId = 1;

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody ItemDto itemDto) {
        userService.findById(userId);
        // Валидация полей
        if (itemDto.getName() == null || itemDto.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Имя не указано
        }

        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Описание не указано
        }

        if (itemDto.getAvailable() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Доступность не указана
        }
        Item item = ItemMapper.mapToNewItem(itemDto);
        item.setId(currentId++);
        item.setOwnerId(userId);
        items.add(item);
        return new ResponseEntity<>(ItemMapper.mapToItemDto(item), HttpStatus.CREATED);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable long itemId, @RequestHeader("X-Sharer-User-Id") long userId, @RequestBody ItemDto itemDto) {
        for (Item item : items) {
            if (item.getId() == itemId && item.getOwnerId() == userId) {
                item.setName(itemDto.getName());
                item.setDescription(itemDto.getDescription());
                item.setAvailable(itemDto.getAvailable());
                return new ResponseEntity<>(ItemMapper.mapToItemDto(item), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable long itemId) {
        return items.stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .map(item -> new ResponseEntity<>(ItemMapper.mapToItemDto(item), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getOwnerId() == userId) {
                result.add(ItemMapper.mapToItemDto(item));
            }
        }
        return result;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam("text") String text) {
        if (text == null || text.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        /*List<ItemDto> result = new ArrayList<>();
        for (Item item : items) {
            // Проверяем, что item доступен и не равен null
            if ((item.getName() != null && item.getName().contains(text) ||
                    item.getDescription() != null && item.getDescription().contains(text)) &&
                    Boolean.TRUE.equals(item.getAvailable())) {
                result.add(ItemMapper.mapToItemDto(item));
            }
        }*/
        List<ItemDto> foundItems = items.stream()
                .filter(item -> (item.getAvailable() != null && item.getAvailable()) && // Проверка доступности
                        (item.getName() != null && item.getName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDescription() != null && item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(foundItems, HttpStatus.OK);
    }
}