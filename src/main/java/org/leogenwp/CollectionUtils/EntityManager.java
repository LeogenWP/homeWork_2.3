package org.leogenwp.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManager {
     private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("my_p");
    private static javax.persistence.EntityManager em = entityManagerFactory.createEntityManager();

    public static javax.persistence.EntityManager get() {
        return em;
    }


}
