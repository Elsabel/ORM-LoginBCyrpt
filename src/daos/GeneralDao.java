/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class GeneralDao<E> implements IDao<E> {

    private Session session;
    private Transaction transaction;
    private SessionFactory sessionFactory;
    public GeneralDao fd;

    public GeneralDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean save(E entity) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(E entity) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            System.out.println(Object.class);
            session.delete(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public List<E> select(String entity) {
        List<E> data = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            data = session
                    .createQuery("from " + entity+" order by 1")
                    .list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return data;
    }

  

    @Override
    public List<E> search(String tabel, String field, String key) {
        List<E> data = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
//                data = session
//                    .createQuery("from "+tabel+" where "+field + " like '%"+key+"%'").list();
            String query = "from " + tabel + " where " + field + "  like :keys";
            data = session.createQuery(query)
                    .setString("keys", "%" + key + "%")
                    .list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return data;
    }

    @Override
    public E selectByField(String tabel, String field1, String field2, String fname, String lname) {
        E entity = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String query = "from " + tabel + " where " + field1 + "  like :keys and "+field2+" like :keyss";
            entity = (E) session.createQuery(query)
                    .setString("keys", fname)
                    .setString("keyss", lname)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public E selectByField(String table, String field, String key) {
        E entity = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String query = "from " + table + " where " + field + " like :key";
            entity = (E) session.createQuery(query)
                    .setString("key", key)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return entity;   }

}