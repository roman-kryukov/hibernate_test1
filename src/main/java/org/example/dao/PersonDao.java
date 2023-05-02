package org.example.dao;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PersonDao {

    private final SessionFactory sessionFactory;

    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction(); // Обязательно открыть транзакцию, иначе будет исключение

        List<Person> people = session.createQuery("select p from Person p", Person.class)
                .getResultList();

        session.getTransaction().commit(); // и закрыть

        return people;
    }
}
