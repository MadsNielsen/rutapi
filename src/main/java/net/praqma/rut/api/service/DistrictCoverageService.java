/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.rut.api.service;


/**
 *
 * @author mads
 */
public class DistrictCoverageService {
    
    public final String org ="hello",area = "hello",district = "hello";
    
    public String get() {
        return String.format( "Coverage for district %s belonging to organisation %s which is in area %s", district, org, area );
    }
}
