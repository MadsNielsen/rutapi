/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.rut.api.service;

import net.praqma.rut.api.model.District;

/**
 *
 * @author Praqma
 */

public class DistrictService extends AbstractService<District> {

    public String getDistricsForOrganisation() {        
        return "getDistricsForOrganisation";
    }

    public String getDistricsForOrganisationInArea() {
        return "getDistricsForOrganisationInArea";
    }

    public District getDistricsForOrganisationInArea(String org, String areaid,  String districtid) {
        return new District(org+areaid+districtid);
    }

    public String getDistrictCoverage(String org, String areaid, String districtid) {
        return "Booohoo";
    }
}
