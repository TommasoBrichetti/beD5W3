package models.DAO;

import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(ItemsDAO.class);

    public void save(User object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(object);

            transaction.commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();

            logger.error("Error saving object: " + object.getClass().getSimpleName(), ex);
            throw ex;

        } finally {
            em.close();
        }

    }

    public void refresh(User object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            em.refresh(object);

        } finally {
            em.close();
        }

    }

    public void delete(User object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.remove(object);

            transaction.commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            logger.error("Error deleting object: " + object.getClass().getSimpleName(), ex);
            throw ex;

        } finally {
            em.close();
        }

    }

    public static User getById(Long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            return em.find(User.class, id);

        } finally {
            em.close();
        }

    }


}
