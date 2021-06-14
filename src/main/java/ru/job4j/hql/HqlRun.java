package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HqlRun implements AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public JobBase save(JobBase jobBase) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(jobBase);
            session.getTransaction().commit();
        }
        return jobBase;
    }

    public Candidate save(Candidate candidate) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(candidate);
            session.getTransaction().commit();
        }
        return candidate;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            candidates = session.createQuery("from Candidate").list();
            session.getTransaction().commit();
        }
        return candidates;
    }

    public Candidate findById(int id) {
        Candidate candidate;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "select c from Candidate c "
                    + "join fetch c.jobBase jb "
                    + "join fetch  jb.jobs "
                    + "where c.id = :id");
            query.setParameter("id", id);
            candidate = (Candidate) query.uniqueResult();
            session.getTransaction().commit();
        }
        return candidate;
    }

    public List<Candidate> findByName(String name) {
        List<Candidate> candidates;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            candidates = session.createQuery(
                    "from Candidate c where c.name = :name")
                    .setParameter("name", name).list();
            session.getTransaction().commit();
        }
        return candidates;
    }

    public boolean update(int id, String name, int exp, int sal) {
        boolean rsl;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "update Candidate "
                    + "set name = :name, experience = :exp, salary = :sal "
                    + "where id = :id")
                    .setParameter("name", name)
                    .setParameter("exp", exp)
                    .setParameter("sal", sal)
                    .setParameter("id", id);
            rsl = query.executeUpdate() > 0;
            session.getTransaction().commit();
        }
        return rsl;
    }

    public boolean delete(int id) {
        boolean rsl;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "delete from Candidate where id = :id")
                    .setParameter("id", id);
            rsl = query.executeUpdate() > 0;
            session.getTransaction().commit();
        }
        return rsl;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
