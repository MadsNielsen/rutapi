/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Praqma
 */

@XmlRootElement
@Entity
public class Method implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(length=3000)
    private String metodologyText;
    
    private String title;
    private int minParticipant = 1;
    private int maxParticipant = Integer.MAX_VALUE;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Question> relevantQuestions;
    
    public Method() {}
    public Method(String metodologyText, String title, int minParticipant, int maxParticipant) {
        this.metodologyText = metodologyText;
        this.minParticipant = minParticipant;
        this.metodologyText = metodologyText;
        this.title = title;
        
    }
    
    @Deprecated
    public Method(String metodologyText, int minParticipant, int maxParticipant) {
        this.maxParticipant = maxParticipant;
        this.minParticipant = minParticipant;
        this.metodologyText = metodologyText;
        this.title = "Manglende titel";
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
     * @return the metodologyText
     */
    public String getMetodologyText() {
        return metodologyText;
    }

    /**
     * @param metodologyText the metodologyText to set
     */
    public void setMetodologyText(String metodologyText) {
        this.metodologyText = metodologyText;
    }

    /**
     * @return the minParticipant
     */
    public int getMinParticipant() {
        return minParticipant;
    }

    /**
     * @param minParticipant the minParticipant to set
     */
    public void setMinParticipant(int minParticipant) {
        this.minParticipant = minParticipant;
    }

    /**
     * @return the maxParticipant
     */
    public int getMaxParticipant() {
        return maxParticipant;
    }

    /**
     * @param maxParticipant the maxParticipant to set
     */
    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the relevantQuestions
     */
    public List<Question> getRelevantQuestions() {
        return relevantQuestions;
    }

    /**
     * @param relevantQuestions the relevantQuestions to set
     */
    public void setRelevantQuestions(List<Question> relevantQuestions) {
        this.relevantQuestions = relevantQuestions;
    }
}
