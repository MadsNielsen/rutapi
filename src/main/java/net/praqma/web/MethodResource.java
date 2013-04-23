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
import net.praqma.web.model.Meeting;
import net.praqma.web.model.Method;
import org.hibernate.Query;
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
    
    @POST
    @Path("/{id}/delete")
    public void delete(@PathParam("id") Long id) {
        Transaction tx = null; 
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
        
            Method m = (Method)session.createQuery("from Method m where m.id = :myid ").setParameter("myid", id).uniqueResult();
            session.delete(m);
            
            tx.commit();
            
        } catch (Exception ex) {
            tx.rollback();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Method showJSON(@PathParam("id") Long id) {     
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        Method m = (Method)session.createQuery("from Method m where m.id = :myid ").setParameter("myid", id).uniqueResult();
        tx.commit();
        return m;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String showHtml(@PathParam("id") Long id) {     
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        
        Method m = (Method)session.createQuery("from Method m where m.id = :myid ").setParameter("myid", id).uniqueResult();
        
        
        StringBuilder builder = new StringBuilder();
        
        builder.append("<html>");
        builder.append("<head></head>");
        builder.append("<body>");
        builder.append(String.format("<h3>%s</h3>", m.getTitle()));
        builder.append(String.format(m.getMetodologyText()));
        builder.append("</body>");
        builder.append("</html>");
        
        tx.commit();
        return builder.toString();
    }
    
    
    @POST
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Method> find(@FormParam("num") int num, @FormParam("type") String type) {
        Meeting m1 = null;
        Transaction tx = null;
        List<Method> selctedMethods = new ArrayList<Method>();  
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            System.out.println("SELECTED TYPE : "+type);
            Query q = session.createQuery("from Meeting me where me.type = :mytype");
            q.setParameter("mytype", type);
            
            
            System.out.println(q.getQueryString());
            
            m1 = (Meeting)q.uniqueResult();
            System.out.println("About to commit!");
            
            System.out.println("Cone comitting!");
            
            if(m1 == null) {
                System.out.println("Whoops! Something went wrong");
            } else if(m1 != null) {
                System.out.println("Found somee methods: "+m1.getApplicableMethods().size());
                for(Method m : m1.getApplicableMethods()) {
                    if(num >= m.getMinParticipant() && num <= m.getMaxParticipant() ) {
                        selctedMethods.add(m);
                    }
                }
            }
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            tx.rollback();
        }
                
              
        
        return selctedMethods;

    }
}
