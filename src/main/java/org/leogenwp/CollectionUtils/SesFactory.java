package org.leogenwp.CollectionUtils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.leogenwp.model.Label;
import org.leogenwp.model.Post;
import org.leogenwp.model.Writer;

public class SesFactory {
    private static Configuration conf = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(Label.class)
            .addAnnotatedClass(Post.class)
            .addAnnotatedClass(Writer.class);
    private static SessionFactory sessionFactory = conf.buildSessionFactory();

    public static SessionFactory get() {
        return sessionFactory;
    }
}
