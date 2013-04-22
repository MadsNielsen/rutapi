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
public class Method implements Serializable {
    private Long id;
    private int minParticipant = 1;
    private int maxParticipant = Integer.MAX_VALUE;
    private String metodologyText;
    
    public Method() { }
    
    public Method(String metodologyText, int minParticipant, int maxParticipant) {
        this.maxParticipant = maxParticipant;
        this.minParticipant = minParticipant;
        this.metodologyText = metodologyText;                
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
}
