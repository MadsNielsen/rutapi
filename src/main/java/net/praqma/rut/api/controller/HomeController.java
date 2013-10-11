/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.rut.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mads
 */

@Controller
public class HomeController {
    
    @RequestMapping(value = "/hello-page", method = {RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("hello"); //name of the jsp-file in the 'page' folder

        String str = "MVC Spring is here!";
        view.addObject("message", str); //adding of str object as 'message' parameter

        return view;
    }
            
}
