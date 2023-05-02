package org.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

// Обобщённый класс, от которого все DAO будут наследоваться.
// В нём будут реализованы стандатрные методы: получение, редактирование, удаление, создание
public class EntityDao<T> {

    private final Class<T> TYPE;
    protected final SessionFactory sessionFactory;

    public EntityDao(Class<T> type, SessionFactory sessionFactory) {
        TYPE = type;
        this.sessionFactory = sessionFactory;
    }

    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction(); // Обязательно открыть транзакцию, иначе будет исключение

        List<T> entities = session.createQuery("select p from " + TYPE.getSimpleName() + " p", TYPE)
                .getResultList();

        session.getTransaction().commit(); // и закрыть

        return entities;
    }
    
    // Получить по id
    // Удалить ...
}
