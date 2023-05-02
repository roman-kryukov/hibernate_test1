package org.example.dao;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PersonDao extends EntityDao<Person> {

    public PersonDao(SessionFactory sessionFactory) {
        super(Person.class, sessionFactory);
    }

}