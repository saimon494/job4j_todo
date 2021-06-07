package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Function;

public class HbmStore implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> findAllItem() {
        return tx(session -> session.createQuery(
                "select distinct i from Item i join fetch i.categories"
        ).list());
    }

    @Override
    public Item findItemById(int id) {
        return tx(session -> session.get(Item.class, id));
    }

    @Override
    public boolean save(Item item) {
        return tx(session -> {
            session.saveOrUpdate(item);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return tx(session -> {
            Item item = session.get(Item.class, id);
            session.delete(item);
            return true;
        });
    }

    @Override
    public List<User> findAllUser() {
        return tx(session -> session.createQuery("FROM ru.job4j.todo.model.User").list());
    }

    public User findUserByEmail(String email) {
        return tx(session ->
                (User) session.createQuery("from ru.job4j.todo.model.User where email = :email")
                        .setParameter("email", email)
                        .uniqueResult()
        );
    }

    @Override
    public boolean save(User user) {
        return tx(session -> {
            session.saveOrUpdate(user);
            return true;
        });
    }

    @Override
    public List<Category> findAllCategory() {
        return tx(session -> session.createQuery(
                "from ru.job4j.todo.model.Category"
        ).list());
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
