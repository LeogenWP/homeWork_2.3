package org.leogenwp.repository.io;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.leogenwp.CollectionUtils.SesFactory;
import org.leogenwp.model.Writer;
import org.leogenwp.repository.WriterRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;


public class JavaIOWriterRepository implements WriterRepository {
    private EntityManager em = org.leogenwp.CollectionUtils.EntityManager.get();

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
        try(Session session = SesFactory.getSession()) {
            session.beginTransaction();
            session.save(writer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
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
        try(Session session = SesFactory.getSession()) {
            session.beginTransaction();
            session.update(writer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
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

