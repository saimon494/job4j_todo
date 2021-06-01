package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface Store {

    List<Item> findAllItem();

    Item findItemById(int id);

    boolean save(Item item);

    boolean delete(int id);

}
