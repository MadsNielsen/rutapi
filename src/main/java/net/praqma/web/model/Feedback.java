/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Praqma
 */
@XmlRootElement
@Entity
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;    
    @Temporal(TemporalType.DATE)
    private Date feedbackDate;
   
    private int grade;
    
    public Feedback() {}
    
    public Feedback(int grade) {
        this.grade = grade;
        this.feedbackDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the feedbackDate
     */
    public Date getFeedbackDate() {
        return feedbackDate;
    }

    /**
     * @param feedbackDate the feedbackDate to set
     */
    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    /**
     * @return the grade
     */
    public int getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }
}
