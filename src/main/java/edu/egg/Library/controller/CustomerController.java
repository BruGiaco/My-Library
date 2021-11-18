
package edu.egg.Library.controller;

import edu.egg.Library.entity.Customer;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.service.CustomerService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService cServ;
    
    @GetMapping
    public ModelAndView show(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("customer");
        List<Customer> custList = cServ.show();
        
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null){
            mav.addObject("done", flashMap.get("done"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("customers", custList);
        return mav;
    }
    
    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView mav = new ModelAndView("CustomerForm");
        mav.addObject("customer", new Customer());
        mav.addObject("title", "Create Customer");
        mav.addObject("action", "save");
        return mav;
    }
    
    @PostMapping("/save")
    public RedirectView saveCustomer(@RequestParam long dni, @RequestParam String name, @RequestParam String lastName, @RequestParam String phoneNumber, RedirectAttributes attr ){
        try {
            cServ.addCustomer(name, lastName, phoneNumber, dni);
            attr.addFlashAttribute("done", "Se agregó correctamente");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/customer");
    }
    
    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable String id){
        ModelAndView mav = new ModelAndView("CustomerForm");
        mav.addObject("customer", cServ.findByID(id));
        mav.addObject("title", "modify customer");
        mav.addObject("action", "edit");
        return mav;
    }
    
    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable String id, RedirectAttributes attr) throws LibraryException {
        try {
            cServ.delete(id);
            attr.addFlashAttribute("done", "Se eliminó el cliente");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/customer");
    }

    @PostMapping("/edit")
    public RedirectView EditCustomer(@RequestParam String id, @RequestParam long dni, @RequestParam String name, @RequestParam String lastName, @RequestParam String phoneNumber, RedirectAttributes attr) {
        try {
            cServ.modify(id, name, lastName, phoneNumber, dni);
            attr.addFlashAttribute("done", "Se modificó el cliente");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/customer");
    }

    @GetMapping("/unsubscribe/{id}")
    public RedirectView unsubscribe(@PathVariable String id, RedirectAttributes attr) {
        try {
            cServ.unsusbcribe(id);
            attr.addFlashAttribute("done", "Se dio de baja.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/customer");
    }

    @GetMapping("/subscribe/{id}")
    public RedirectView subscribe(@PathVariable String id, RedirectAttributes attr) {
        try {
            cServ.susbcribe(id);
            attr.addFlashAttribute("done", "Se dio de alta.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/customer");
    }
}    
