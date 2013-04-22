/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.praqma.web.model.Method;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Praqma
 */
@Path("/methods")
public class MethodResource {
    
    private Method[] methods = new Method[] { 
        new Method("Method 1", 1, 4),
        new Method("Method 2", 5, 12),
        new Method("Method 3", 2, 8)
    };
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Method> list() {
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        List objects = session.createQuery("from Method").list();
        
        tx.commit();
        return (List<Method>)objects;
    }
    
    @GET
    @Path("/bootstrap")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Method> bootstrap() {
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        for(Method m : methods) {
            session.save(m);
        }
        tx.commit();
        return Arrays.asList(methods);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Method show(@PathParam("id") Long id) {     
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        Method m = (Method)session.createQuery("from Method m where m.id = :myid ").setParameter("myid", id).uniqueResult();
        tx.commit();
        return m;
    }
    
    @POST
    @Path("/find")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Method> find(@FormParam("num") int num) {
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        List<Method> objects = (List<Method>)session.createQuery("from Method").list();
        List<Method> selctedMethods = new ArrayList<Method>();
        
        tx.commit();
        for(Method m : objects) {
            if(num >= m.getMinParticipant() && num <= m.getMaxParticipant() ) {
                selctedMethods.add(m);
            }
        }
        return selctedMethods;

    }
}
