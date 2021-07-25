package org.leogenwp.repository.io;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.leogenwp.CollectionUtils.SesFactory;
import org.leogenwp.model.Label;
import org.leogenwp.repository.LabelRepository;
import java.util.ArrayList;
import java.util.List;

public class JavaIOLabelRepository  implements LabelRepository {

    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        try(Session session = SesFactory.getSession()) {
            session.beginTransaction();
            labels = session.createQuery("FROM Label").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return labels;
    }

    @Override
    public Label save(Label label) {
        try(Session session = SesFactory.getSession()) {
            session.beginTransaction();
            session.save(label);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public Label getById(Integer id) {
       Label label = new Label();
        try(Session session = SesFactory.getSession()) {
            session.beginTransaction();
             label = (Label) session.get(Label.class,id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

       return label;
    }

    @Override
    public Label update(Label label) {
        try(Session session = SesFactory.getSession()) {
            session.beginTransaction();
            session.update(label);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public void deleteById(Integer id) {
        try(Session session = SesFactory.getSession()) {
            session.beginTransaction();
            Label label = (Label) session.get(Label.class,id);
            session.remove(label);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
