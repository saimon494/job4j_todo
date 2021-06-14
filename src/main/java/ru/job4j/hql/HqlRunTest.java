package ru.job4j.hql;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HqlRunTest {

    private HqlRun hql;

    @Before
    public void setUp() {
        hql = new HqlRun();
    }

    @Test
    public void findAll() {
        var can1 = Candidate.of("Vasya", 1, 20);
        var can2 = Candidate.of("Vladimir", 2, 30);
        var can3 = Candidate.of("Vitya", 3, 40);
        hql.save(can1);
        hql.save(can2);
        hql.save(can3);
        var expected = List.of(can1, can2, can3);
        var out = hql.findAll();
        assertThat(out, is(expected));
        hql.delete(can1.getId());
        hql.delete(can2.getId());
        hql.delete(can3.getId());
    }

    @Test
    public void findById() {
        var can = Candidate.of("Vladimir", 2, 30);
        hql.save(can);
        var rsl = hql.findById(can.getId());
        assertThat(rsl, is(can));
        hql.delete(can.getId());
    }

    @Test
    public void update() {
        var can = Candidate.of("Vladimir", 2, 30);
        hql.save(can);
        hql.update(can.getId(), "Vova", 3, 50);
        var rsl = hql.findByName("Vova");
        assertThat(rsl.size(), is(1));
        hql.delete(can.getId());
    }
}