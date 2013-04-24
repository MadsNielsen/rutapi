/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.praqma.web.model.Question;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Praqma
 */
@Path("/questions")
public class QuestionResource {
    
    private Question[] questions = new Question[] { 
        new Question("Why are my pants purple?",2),
        new Question("Why come the answer to the universe, and everything, is 42?",3),
        new Question("Why does it rain on the moon?",1),
        new Question("Why are roses usually red?",4)
    };
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> listQuestion() {
        
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();        
        List objects = session.createQuery("from Question").list();        
        tx.commit();
        return (List<Question>)objects;
    }
    
    @GET
    @Path("/bootstrap")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> bootstrap() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        for(Question m : questions) {
            session.save(m);
        }
        tx.commit();
        return Arrays.asList(questions);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Question showQuestion(@PathParam("id") Long id) {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();        
        Question qs = (Question)session.createQuery("from Question q where q.id = :id").setParameter("id", id).uniqueResult();        
        tx.commit();
        return qs;
    }
}
