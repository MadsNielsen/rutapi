/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.rut.api.service;

/**
 *
 * @author mads
 */
public class AbstractService<T extends Object> {
    public <T> T getById(Long id) {
        return null;
    }
    
    public <T> T save(T object) {
        return object;
    }
    
    
}
