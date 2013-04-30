/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.praqma.web.model.Meeting;
import net.praqma.web.model.Method;
import net.praqma.web.model.Question;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Praqma
 */

@Path("/meetings")
public class MeetingResource {
 
    Meeting[] meetings = new Meeting[] {
        new Meeting("Retrospective"),
        new Meeting("Fagmøde")
    };
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        return "<h3>Meetings</h3>";
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Meeting showJSON(@PathParam("id") Long id) {
        Session s = HibernateUtil.getCurrentSession();               
        Transaction tx = HibernateUtil.getCurrentSession().beginTransaction();
        Meeting m = (Meeting) HibernateUtil.getCurrentSession().createQuery("from Meeting m where m.id = :id").setParameter("id", id).uniqueResult();
        tx.commit();        
        return m;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Meeting showHtml(@PathParam("id") Long id) {
        Session s = HibernateUtil.getCurrentSession();               
        Transaction tx = HibernateUtil.getCurrentSession().beginTransaction();
        Meeting m = (Meeting) HibernateUtil.getCurrentSession().createQuery("from Meeting m where m.id = :id").setParameter("id", id).uniqueResult();
        tx.commit();        
        return m;
    }

    @POST
    @Path("/{id}/delete")
    public void deletePost(@PathParam("id") Long id) {
        Session s = HibernateUtil.getCurrentSession();               
        Transaction tx = HibernateUtil.getCurrentSession().beginTransaction();
        Meeting m = (Meeting) HibernateUtil.getCurrentSession().createQuery("from Meeting m where m.id = :id").setParameter("id", id).uniqueResult();
        s.delete(m);
        tx.commit();
    }
    
    @GET
    @Path("/{id}/delete")
    public boolean deleteGet(@PathParam("id") Long id) {
        Session s = HibernateUtil.getCurrentSession();               
        Transaction tx = HibernateUtil.getCurrentSession().beginTransaction();
        Meeting m = (Meeting) HibernateUtil.getCurrentSession().createQuery("from Meeting m where m.id = :id").setParameter("id", id).uniqueResult();
        s.delete(m);
        tx.commit();
        return true;
    }
    
    /**
     * 
     * @return a request for the 
     */
    @POST
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
        Session session = null;
        Transaction tx = null;
        try {
            Question[] qs = {
                new Question("Why is the answer to the universe, life and everything 42?", 222),
                new Question("Why is the grass always greener on the other side?",2)

            };

            String text ="<img src=\"images/fishbowl.png\"/><p>We are uncovering better ways of developing software by doing it and helping others do it."+ 
                            "Through this work we have come to value:</p><ul>" +
                            "<li><b>Individuals and interactions</b> over processes and tools</li>" +
                            "<li><b>Working software</b> over comprehensive documentation</li>" +
                            "<li><b>Customer collaboration</b> over contract negotiation</li>" +
                            "<li><b>Responding to change</b> over following a plan</li>" +
                            "</ul>"; 

            session  = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            for(Meeting m : meetings) {
                Method me2 = new Method(text, String.format("Method for %s",m.getType()), new Random().nextInt(3)+1, new Random().nextInt(5)+10);
                me2.setRelevantQuestions(Arrays.asList(qs));
                Method me3 = new Method(text, String.format("Method for %s",m.getType()), new Random().nextInt(3)+1, new Random().nextInt(5)+10);
                me3.setRelevantQuestions(Arrays.asList(qs));
                m.setApplicableMethods(new HashSet<Method>(Arrays.asList(me2,me3)));
                session.save(m);
            }
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        }
        return Arrays.asList(meetings);
    }
}
