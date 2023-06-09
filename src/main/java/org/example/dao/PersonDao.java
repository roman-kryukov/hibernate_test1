package org.example.dao;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

// Классы наследники нужны для реализации специфичных функций для данных сущностей
public class PersonDao extends EntityDao<Person> {

    public PersonDao(SessionFactory sessionFactory) {
        super(Person.class, sessionFactory);
    }

}
