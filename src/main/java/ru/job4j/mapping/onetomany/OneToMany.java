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
            var bmw = new Brand("BMW");
            var model1 = new Model("M3");
            model1.setBrand(bmw);
            var model2 = new Model("M5");
            model2.setBrand(bmw);
            var model3 = new Model("X1");
            model3.setBrand(bmw);
            var model4 = new Model("X5");
            model4.setBrand(bmw);
            var model5 = new Model("X7");
            model5.setBrand(bmw);
            session.save(model1);
            session.save(model2);
            session.save(model3);
            session.save(model4);
            session.save(model5);
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
