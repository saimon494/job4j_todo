package ru.job4j.hql;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HqlRunTest {

    private HqlRun hql;
    private JobBase jobBase;

    @Before
    public void setUp() {
        hql = new HqlRun();
        jobBase = new JobBase("Vacancies");
        jobBase.add(new Job("Job1"));
        jobBase.add(new Job("Job2"));
        jobBase.add(new Job("Job3"));
    }

    @Test
    public void findAll() {
        hql.save(jobBase);
        var can1 = Candidate.of("Vasya", 1, 20);
        var can2 = Candidate.of("Vladimir", 2, 30);
        var can3 = Candidate.of("Vitya", 3, 40);
        can1.setJobBase(jobBase);
        can2.setJobBase(jobBase);
        can3.setJobBase(jobBase);
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
        hql.save(jobBase);
        var can = Candidate.of("Vladimir", 2, 30);
        can.setJobBase(jobBase);
        hql.save(can);
        var rsl = hql.findById(can.getId());
        assertThat(rsl, is(can));
        hql.delete(can.getId());
    }

    @Test
    public void update() {
        hql.save(jobBase);
        var can = Candidate.of("Vladimir", 2, 30);
        can.setJobBase(jobBase);
        hql.save(can);
        hql.update(can.getId(), "Vova", 3, 50);
        var rsl = hql.findByName("Vova");
        assertThat(rsl.size(), is(1));
        hql.delete(can.getId());
    }
}