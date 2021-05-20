package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface Store {

    List<Item> findAllItem();

    Item findItemById(int id);

    Item save(Item item);

    void delete(int id);

    void update(Item item);
}
