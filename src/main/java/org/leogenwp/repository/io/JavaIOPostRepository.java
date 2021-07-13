package org.leogenwp.repository.io;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.leogenwp.CollectionUtils.SesFactory;
import org.leogenwp.model.Post;
import org.leogenwp.model.PostStatus;
import org.leogenwp.repository.PostRepository;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JavaIOPostRepository implements PostRepository {
    private final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SessionFactory sessionFactory = SesFactory.get();
    private EntityManager em = org.leogenwp.CollectionUtils.EntityManager.get();
    private  Session session = null;


    @Override
    public List<Post> getAll() {
        List<Post> posts;
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createQuery("SELECT  e FROM Post e left JOIN FETCH e.labels t");
        posts =query.getResultList();
        transaction.commit();
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
        Post post = em.find(Post.class, postId);
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
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createQuery("Delete FROM Post WHERE id = ?1");
        query.setParameter(1,id);
        query.executeUpdate();
        transaction.commit();
    }

}
