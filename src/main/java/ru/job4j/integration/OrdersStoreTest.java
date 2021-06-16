package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OrdersStoreTest {

    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        var builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_002.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        var store = new OrdersStore(pool);
        store.save(Order.of("name", "description"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenFindById() {
        var store = new OrdersStore(pool);
        store.save(Order.of("name", "description"));
        Order rsl = store.findById(1);
        assertThat(rsl.getName(), is("name"));
        assertThat(rsl.getDescription(), is("description"));
    }

    @Test
    public void whenUpdate() {
        var store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        var order = Order.of("name2", "description2");
        store.update(1, order);
        Order rsl = store.findById(1);
        assertThat(rsl.getName(), is("name2"));
        assertThat(rsl.getDescription(), is("description2"));
    }

    @Test
    public void whenFindByName() {
        var store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        store.save(Order.of("name1", "description2"));
        List<Order> all = store.findByName("name1");
        assertThat(all.size(), is(2));
        assertThat(all.get(0).getName(), is("name1"));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(1).getName(), is("name1"));
        assertThat(all.get(1).getDescription(), is("description2"));
    }
}