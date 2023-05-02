package org.example.dao;

import org.example.model.Car;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

// Классы наследники нужны для реализации специфичных функций для данных сущностей
public class CarDao extends EntityDao<Car> {

    public CarDao(SessionFactory sessionFactory) {
        super(Car.class, sessionFactory);
    }

    // Получать машины через новую транзакцию нужно потому
    // что в поле cars используется ленивая загрузка (fetch = FetchType.LAZY).
    // Без открытия новой транзакции и привязки к новой сессии hibernate не сможет
    // проинициализировать cars.
    public List<Car> getCarsByPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        // Привязка person к сессии (помощение в persistence context)
        person = (Person) session.merge(person);

        // Обязательно использовать метод для инициализации
        // Hibernate.initialize(<объект для инициализации>).
        Hibernate.initialize(person.getCars());

        session.getTransaction().commit();
        return person.getCars();
    }

}
