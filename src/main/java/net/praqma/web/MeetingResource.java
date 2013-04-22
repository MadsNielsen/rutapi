/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.praqma.web.model.Meeting;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Praqma
 */

@Path("/meetings")
public class MeetingResource {
 
    Meeting[] meetings = new Meeting[] {
        new Meeting("Retropspective"),
        new Meeting("Fagmøde")
    };
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Meeting> list() {
        return null;        
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Meeting show() {
        return null;        
    }
    
    /**
     * 
     * @return a request for the 
     */
    @GET
    @Path("/types")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> types() {
        Transaction tx = HibernateUtil.getCurrentSession().beginTransaction();
        List<String> strings = HibernateUtil.getCurrentSession().createQuery("Select distinct m.type from Meeting m").list();
        tx.commit();
        return strings;
    }
    
    @GET
    @Path("/bootstrap")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Meeting> bootstrap() {
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        for(Meeting m : meetings) {
            session.save(m);
        }
        tx.commit();
        return Arrays.asList(meetings);
    }
}
