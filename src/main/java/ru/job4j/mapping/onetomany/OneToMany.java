package ru.job4j.mapping.onetomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class OneToMany {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            session.save(new Model("M3"));
            session.save(new Model("M5"));
            session.save(new Model("X1"));
            session.save(new Model("X5"));
            session.save(new Model("X7"));
            var bmw = new Brand("BMW");
            bmw.addModel(session.find(Model.class, 1));
            bmw.addModel(session.find(Model.class, 2));
            bmw.addModel(session.find(Model.class, 3));
            bmw.addModel(session.find(Model.class, 4));
            bmw.addModel(session.find(Model.class, 5));
            session.save(bmw);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
