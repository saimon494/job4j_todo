package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface Store {

    List<Item> findAllItem();

    Item findItemById(int id);

    boolean save(Item item);

    boolean update(int id);

    boolean delete(int id);

    List<User> findAllUser();

    User findUserByEmail(String email);

    boolean save(User user);

    List<Category> findAllCategory();
}
