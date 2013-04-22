/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web.model;

import java.io.Serializable;
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
    private Long id;
    private String questionText;
    
    public Question() { }
    
    public Question(Long id, String questionText) {
        this.id = id;
        this.questionText = questionText;
    }

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
    
}
