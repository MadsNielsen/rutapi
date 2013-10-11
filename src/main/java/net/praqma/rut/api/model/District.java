/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.rut.api.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Praqma
 */
@XmlRootElement
@Entity
public class District implements Serializable {
    
    public District() { }

    @Id
    private String districtId;
    
    public District(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

}
