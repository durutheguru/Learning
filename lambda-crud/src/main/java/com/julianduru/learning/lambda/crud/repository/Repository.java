package com.julianduru.learning.lambda.crud.repository;

import com.julianduru.learning.lambda.crud.models.Book;
import com.julianduru.learning.lambda.crud.util.HibernateUtil;
import org.hibernate.Session;

/**
 * created by julian on 03/02/2023
 */
public class Repository<T> {


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


}
