package ru.job4j.mapping.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ManyToMany {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public void save() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            var author1 = new Author("author1");
            var author2 = new Author("author2");
            var book1 = new Book("book1");
            var book2 = new Book("book2");
            var book3 = new Book("book3");
            author1.addBook(book1);
            author1.addBook(book2);
            author1.addBook(book3);
            author2.addBook(book1);
            author2.addBook(book2);
            session.persist(author1);
            session.persist(author2);
            session.getTransaction().commit();
        }
    }

    public void delete() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.remove(session.load(Author.class, 1));
            session.getTransaction().commit();
        }
    }

    public static void main(String[] args) {
        var many = new ManyToMany();
//        many.save();
        many.delete();
    }
}
