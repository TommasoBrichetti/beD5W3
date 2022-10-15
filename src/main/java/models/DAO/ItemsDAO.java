package models.DAO;

import models.Availability;
import models.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;



public class ItemsDAO {
    private static final Logger logger = LoggerFactory.getLogger(ItemsDAO.class);

    public void save(Item object) {
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

    //dovrebbe andare
    public void toggle(Long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Item object = em.find(Item.class, id);

            if (object.getAvailability() == Availability.DISPONIBILE) {
            object.setAvailability(Availability.PRESTATO);
            }else{
                object.setAvailability(Availability.DISPONIBILE);
            }

            transaction.commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();

            throw ex;

        } finally {
            em.close();
        }

    }


    public static void refresh(Item object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            em.refresh(em.merge(object));

        } finally {
            em.close();
        }

    }

    public void delete(Item object) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.remove(em.contains(object) ? object : em.merge(object));

            transaction.commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            logger.error("Error deleting object: " + object.getClass().getSimpleName(), ex);
            throw ex;

        } finally {
            em.close();
        }

    }

    public Item getById(Long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            return em.find(Item.class, id);

        } finally {
            em.close();
        }
    }

    public static List<Item> getItemByTile(String title) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Query query = em.createNamedQuery( "findByTitle" );

            query.setParameter( "title", title );
            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public static List<Item> getItemByAuthor(String author) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {

            Query query = em.createNamedQuery( "libroByAuthor" );

            query.setParameter( "author", author );
            return query.getResultList();

        } finally {
            em.close();
        }
    }

}

