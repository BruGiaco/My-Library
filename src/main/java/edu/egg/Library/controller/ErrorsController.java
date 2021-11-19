package edu.egg.Library.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showErrors(HttpServletResponse hsr) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("code", hsr.getStatus());
        String msg = "";
        switch (hsr.getStatus()) {
            case 404:
                msg = "Page not found";
                break;
            case 500:
                msg = "Error server comunication";
                break;
            case 403:
                msg = "Access denied";
                break;
            default:
                msg = "General error";
        }
        mav.addObject("msg", msg);
        return mav;
    }

}
