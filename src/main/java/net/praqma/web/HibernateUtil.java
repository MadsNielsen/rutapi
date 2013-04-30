/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web;

import java.util.Collection;
import java.util.HashMap;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author Praqma
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration().configure(HibernateUtil.class.getResource("hibernate.cfg.xml"));
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException he) {
            System.err.println("Error creating Session: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getCurrentSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public static <T extends Object> T getSingle(String query, HashMap<String, Object> parameters )  {
        T object = null;
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            tx = session.beginTransaction();
            Query q = session.createQuery(query);
            if(parameters != null) {
                for(String s : parameters.keySet()) {
                    q = q.setParameter(s, parameters.get(s));
                }
            }
            object = (T)q.uniqueResult();        
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if(tx != null) {
                tx.rollback();
            }
        } 
        
        return object;
    }
    
    public static <T extends Object> T saveSingle(T t) {
        T object = null;        
        Transaction tx = HibernateUtil.getCurrentSession().beginTransaction();
        try {
            object = (T)HibernateUtil.getCurrentSession().save(t);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if(tx != null) {
                tx.rollback();
            }
        }
        
        return object;        
    }
    
    public static <T extends Collection<?>> T getList(String query, HashMap<String, Object> parameters )  {
        T object = null;
        Transaction tx = null;
        Session session = null;
        try {
            tx = HibernateUtil.getCurrentSession().beginTransaction();
            Query q = HibernateUtil.getCurrentSession().createQuery(query);
            for(String s : parameters.keySet()) {
                q = q.setParameter(s, parameters.get(s));
            }
            object = (T)q.list();
            tx.commit();
        
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return object;
    }
   
}
