/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import net.praqma.web.model.Feedback;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author Praqma
 */
@Path("/feedback")
public class FeedbackResource {
    public final static SimpleDateFormat simpleFormatter = new SimpleDateFormat();
    public final static Long HOURINMILIS = 1000*60*60L;
    
    @POST
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
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Feedback getFeedback(@PathParam("id") Long id) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return HibernateUtil.getSingle("from Feedback f where f.id = :id", params);
    }
    
    @GET
    @Path("/since/{date_stamp}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Feedback> listFeedbackSinceDateStamp(@PathParam("date_stamp") Long dateStamp, @QueryParam("limit") Integer limit) throws ParseException {
        List<Feedback> feedback = new ArrayList<Feedback>();
        Transaction tx = HibernateUtil.getCurrentSession().beginTransaction();
        Criteria c = HibernateUtil.getCurrentSession().createCriteria(Feedback.class);
        
        Date argDate = new Date(dateStamp);
        Date hourAgo = new Date(argDate.getTime()-FeedbackResource.HOURINMILIS);
        c.add(Restrictions.between("feedbackDate", hourAgo, argDate));
        if(limit != null && limit != 0) {
            c.setMaxResults(limit);
            c.addOrder(Order.desc("feedbackDate"));
            
        } 
        feedback.addAll(c.list());
        System.out.println("Size of recent feedback: "+feedback.size());
        tx.commit();
        
        return feedback;
    }
    
    @GET
    @Path("/average")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPollingAverage() {
        Double average = 0d;
        String query = "select avg(f.grade) from Feedback f";
        average = HibernateUtil.getSingle(query, null);
        
        
        return average.toString();
    }
    
    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPollingCount() {
        Long count = 0L;
        String query = "select count(f) from Feedback f";
        count = HibernateUtil.getSingle(query, null);                
        return count.toString();
    }
    
}
