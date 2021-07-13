package org.leogenwp.repository.io;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.leogenwp.CollectionUtils.SesFactory;
import org.leogenwp.model.Writer;
import org.leogenwp.repository.WriterRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;


public class JavaIOWriterRepository implements WriterRepository {
    private SessionFactory sessionFactory = SesFactory.get();
    private EntityManager em = org.leogenwp.CollectionUtils.EntityManager.get();
    private Session session = null;

    @Override
    public List<Writer> getAll() {
        List<Writer> writers ;
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createQuery("SELECT p FROM Writer p left JOIN FETCH p.posts ");
        writers =query.getResultList();
        transaction.commit();
        return writers;
    }

    @Override
    public Writer save(Writer writer) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(writer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return writer;
    }

    @Override
    public Writer getById(Integer writerId) {
        Writer writer = em.find(Writer.class, writerId);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(writer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return writer;
    }

    @Override
    public void deleteById(Integer id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createQuery("Delete FROM Writer WHERE id = ?1");
        query.setParameter(1,id);
        query.executeUpdate();
        transaction.commit();
    }
}

