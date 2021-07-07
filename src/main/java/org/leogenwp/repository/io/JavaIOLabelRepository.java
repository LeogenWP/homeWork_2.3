package org.leogenwp.repository.io;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.leogenwp.model.Label;
import org.leogenwp.repository.LabelRepository;
import java.util.ArrayList;
import java.util.List;

public class JavaIOLabelRepository  implements LabelRepository {
    private Configuration conf = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Label.class);
    private SessionFactory sessionFactory = conf.buildSessionFactory();
    private  Session session = null;

    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            labels = session.createQuery("FROM Label").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return labels;
    }

    @Override
    public Label save(Label label) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(label);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return label;
    }

    @Override
    public Label getById(Integer id) {
       Label label = new Label();
       try {
            session = sessionFactory.openSession();
            session.beginTransaction();
             label = (Label) session.get(Label.class,id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
       return label;
    }

    @Override
    public Label update(Label label) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(label);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return label;
    }

    @Override
    public void deleteById(Integer id) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Label label = (Label) session.get(Label.class,id);
            session.remove(label);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
