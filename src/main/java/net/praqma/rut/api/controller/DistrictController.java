/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.rut.api.controller;

import net.praqma.rut.api.service.DistrictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author mads
 */
@Controller
@RequestMapping("/district")
public class DistrictController {
    
    private DistrictService service;
    
    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST }, produces = "text/html")
    @ResponseBody
    public String getById() {
        return "Booohooo!";
    }
}
