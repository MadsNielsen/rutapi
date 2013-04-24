/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Praqma
 */
@XmlRootElement
@Entity
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private int importance;
    
    @Column(length = 1000)
    private String questionText;
    
    public Question() { }
    
    public Question(String questionText, int importance) {
        this.questionText = questionText;
        this.importance = importance;
    }
    
    public Question(String questionText) {
        this.questionText = questionText;
        this.importance = 0;
    }

    /**
     * @return the id
     */
        public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * @param questionText the questionText to set
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * @return the importance
     */
    public int getImportance() {
        return importance;
    }

    /**
     * @param importance the importance to set
     */
    public void setImportance(int importance) {
        this.importance = importance;
    }
    
}
