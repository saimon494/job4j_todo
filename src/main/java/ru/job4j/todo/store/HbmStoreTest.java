package ru.job4j.todo.store;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.todo.model.Item;

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
        Item item = new Item("task 1");
        Item item2 = new Item("task 2");
        store.save(item);
        store.save(item2);
        List<Item> expected = List.of(item, item2);
        List<Item> out = store.findAllItem();
        assertThat(out, is(expected));
        store.delete(item.getId());
        store.delete(item2.getId());
    }

    @Test
    public void save() {
        Item item = new Item("task");
        store.save(item);
        Item rsl = store.findItemById(item.getId());
        assertThat(rsl, is(item));
        store.delete(item.getId());
    }

    @Test
    public void update() {
        Item item = new Item("task 1");
        store.save(item);
        item.setDescription("task 2");
        item.setDone(false);
        store.save(item);
        Item rsl = store.findItemById(item.getId());
        assertThat(rsl, is(item));
        store.delete(item.getId());
    }
}