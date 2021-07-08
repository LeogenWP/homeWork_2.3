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


    private final String save = "INSERT INTO posts (content,created,updated,post_status) VALUES('%s','%s','%s','%s')";
    private final String getById = "SELECT b.id as post_id ,content,created,updated,post_status,c.id as label_id ,c.description " +
            "                    FROM posts_labels a right join  posts b" +
            "                    on a.post_id = b.id" +
            "                    left join labels c" +
            "                    on a.label_id = c.id where b.id = %d";
    private final String update = "UPDATE posts SET content ='%s',updated = '%s' ,post_status = '%s' where id = %d";
    private final String deleteById = "delete from labels where id = %d";

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try {
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
        }
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
        Post post = new Post();
        post.setLabels(new ArrayList<Label>());
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()
        ) {
            String sql =String.format(getById,postId);
            ResultSet rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                post.setId(rs.getInt(1));
                post.setContent(rs.getString("content"));
                post.setCreated(rs.getString("created"));
                post.setUpdated(rs.getString("updated"));
                if (rs.getString("post_status").equals("ACTIVE")) {
                    post.setPostStatus(PostStatus.ACTIVE);
                } else if (rs.getString("post_status").equals("UNDER_REVIEW")) {
                    post.setPostStatus(PostStatus.UNDER_REVIEW);
                } else if (rs.getString("post_status").equals("DELETED")) {
                    post.setPostStatus(PostStatus.DELETED);
                } else {
                    post.setPostStatus(PostStatus.ACTIVE);
                }
                if(rs.getInt(6)!=0){
                    Label label = new Label(rs.getInt(6),rs.getString(7));
                    post.addLabel(label);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
