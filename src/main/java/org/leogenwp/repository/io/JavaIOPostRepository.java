package org.leogenwp.repository.io;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.leogenwp.model.Label;
import org.leogenwp.model.Post;
import org.leogenwp.model.PostStatus;
import org.leogenwp.repository.LabelRepository;
import org.leogenwp.repository.PostRepository;
import org.leogenwp.CollectionUtils.ConnectDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JavaIOPostRepository implements PostRepository {
    private final LabelRepository labelRepository = new JavaIOLabelRepository();
    private final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Configuration conf = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Label.class);
    private SessionFactory sessionFactory = conf.buildSessionFactory();
    private  Session session = null;

    private EntityManager em = org.leogenwp.CollectionUtils.EntityManager.get();


    @Override
    public List<Post> getAll() {
        List<Post> posts;
        Query query = em.createQuery("SELECT  e FROM Post e left JOIN FETCH e.labels t");
        posts =query.getResultList();
       /* try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            posts = session.createQuery("FROM Post").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }*/
        return posts;
    }

    @Override
    public Post save(Post post) {
        Date now = new Date();
        String strDate = sdfDate.format(now);
        post.setCreated(strDate);
        post.setUpdated(strDate);
        post.setPostStatus(PostStatus.ACTIVE);

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(post);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return post;
    }

    @Override
    public Post getById(Integer postId) {
        Post post = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            post = (Post) session.get(Post.class,postId);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Date now = new Date();
            String strDate = sdfDate.format(now);
            post.setUpdated(strDate);
            session.update(post);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return post;
    }

    @Override
    public void deleteById (Integer id){
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()) {
            String sql = String.format("delete from posts_labels  where post_id = %d", id);
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()) {
            String sql = String.format("delete from posts where id = %d",id);
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
