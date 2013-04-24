/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.praqma.web.model.Feedback;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Praqma
 */
@Path("/feedback")
public class FeedbackResource {
    public final static Long HOURINMILIS = 1000*60*60L;
    
    @GET
    @Path("/create/{grade_param}")
    @Produces(MediaType.TEXT_PLAIN)
    public String createFeedback(@PathParam("grade_param") int grade) {
        Feedback fb = new Feedback(grade);
        if (HibernateUtil.saveSingle(fb) == null) {
            return "false";
        } else {
            return "true";
        }
    }
    
    
    @Path("/since/{date_stamp}")
    public List<Feedback> listFeedback(@PathParam("date_stamp") Long dateStamp) {        
        //TODO:Implement it so that all feedback since certain time from NOW are collected. Currently just selects everything
        String query = "from Feedback fb where ";
        
        Criteria c = HibernateUtil.getCurrentSession().createCriteria(Feedback.class);
        c.add(Restrictions.gt("feedbackDate", dateStamp-FeedbackResource.HOURINMILIS));
                
        
        List<Feedback> feedback = HibernateUtil.getList(query, null);
        return feedback;
    }
}
