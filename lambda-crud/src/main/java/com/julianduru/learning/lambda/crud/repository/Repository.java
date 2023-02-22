package com.julianduru.learning.lambda.crud.repository;

import com.julianduru.learning.lambda.crud.util.HibernateUtil;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * created by julian on 03/02/2023
 */
public abstract class Repository<T> {


    public T save(T object) throws Exception {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        session.persist(object);

        transaction.commit();
        session.close();

        return object;
    }


    public T save(Session session, T object) throws Exception {
        session.persist(object);
        return object;
    }


    public List<T> findAll() {
        try {
            var session = HibernateUtil.getSessionFactory().openSession();

            var list = session.createQuery(
                "from " + getEntityName(), getEntityClass()
            ).list();

            session.close();

            return list;
        }
        catch (Exception e) {
            throw new RuntimeException("Error while fetching all " + getEntityName() + "s", e);
        }
    }


    public List<T> findAll(Session session) {
        return session.createQuery(
            "from " + getEntityName(), getEntityClass()
        ).list();
    }


    public T delete (T object) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        session.remove(object);

        transaction.commit();
        session.close();

        return object;
    }


    public String getEntityName() {
        return getEntityClass().getSimpleName();
    }


    public Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    }


}


