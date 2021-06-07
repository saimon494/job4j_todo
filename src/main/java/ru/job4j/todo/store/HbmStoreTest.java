package ru.job4j.todo.store;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HbmStoreTest {

    private Store store;

    @Before
    public void setUp() {
        store = new HbmStore();
    }

    @Test
    public void findAllItem() {
        var item = new Item("task 1");
        var item2 = new Item("task 2");
        store.save(item);
        store.save(item2);
        var expected = List.of(item, item2);
        var out = store.findAllItem();
        assertThat(out, is(expected));
        store.delete(item.getId());
        store.delete(item2.getId());
    }

    @Test
    public void findItemById() {
        var item = new Item("task");
        store.save(item);
        var rsl = store.findItemById(item.getId());
        assertThat(rsl, is(item));
        store.delete(item.getId());
    }

    @Test
    public void updateItem() {
        var item = new Item("task 1");
        store.save(item);
        item.setDescription("task 2");
        item.setDone(false);
        store.save(item);
        var rsl = store.findItemById(item.getId());
        assertThat(rsl, is(item));
        store.delete(item.getId());
    }

    @Test
    public void findAllUser() {
        var user1 = new User("Ivan", "ivan@mail.ru", "123");
        var user2 = new User("Vasya", "vasya@mail.ru", "123");
        store.save(user1);
        store.save(user2);
        var expected = List.of(user1, user2);
        var out = store.findAllUser();
        assertThat(out, is(expected));
    }

    @Test
    public void findAllCategory() {
        var out = store.findAllCategory();
        assertThat(out.size(), is(4));
    }
}