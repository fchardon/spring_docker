package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Properties;

/**
 * Created by florent on 14/11/2016.
 */
@Controller
public class HelloController {

    @Autowired
    AppConfig appConfig;

    Properties properties;

    public void name() {
       // properties.load(getServletContext().getResourceAsStream("/WEB-INF/properties/sample.properties"));
    }


      @RequestMapping(value = "/", method = RequestMethod.GET)
        public String printWelcome(ModelMap model) {

            model.addAttribute("title", "Spring 3 MVC Hello World");
            model.addAttribute("version", appConfig.getVersion());
            model.addAttribute("env", appConfig.getEnv());

          return "hello";

      }

   /* @RequestMapping(value = "/msg", method = RequestMethod.GET)
    public ModelAndView hello() {

        ModelAndView model = new ModelAndView();
        model.setViewName("hello");
        model.addObject("msg", "TEST");

        return model;

    }*/



}
