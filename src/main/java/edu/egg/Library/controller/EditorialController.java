package edu.egg.Library.controller;

import edu.egg.Library.entity.Editorial;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.service.EditorialService;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialService eServ;

    @GetMapping("")
    public ModelAndView showEditorial(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("editorial");
        List<Editorial> listEditorial = eServ.EdList();

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("done", flashMap.get("done"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("editorials", listEditorial);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createEditorial() {
        ModelAndView mav = new ModelAndView("EditorialForm");
        mav.addObject("editorial", new Editorial());
        mav.addObject("title", "Create Editorial");
        mav.addObject("action", "save");
        return mav;
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modifyEditorial(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("EditorialForm");
        mav.addObject("editorial", eServ.findById(id));
        mav.addObject("title", "Modify Editorial");
        mav.addObject("action", "edit");

        return mav;
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable String id, RedirectAttributes attr) throws LibraryException {
        try {
            eServ.delete(id);
            attr.addFlashAttribute("done", "Se eliminó correctamente la editorial");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/editorial");
    }

    @PostMapping("/save")
    public RedirectView saveEditorial(@RequestParam String name, RedirectAttributes attr) {
        try {
            eServ.addEditorial(name);
            attr.addFlashAttribute("done", "Se agregó correctamente");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/editorial");
    }

    @PostMapping("/edit")
    public RedirectView EditEditorial(@RequestParam String id, @RequestParam String name, RedirectAttributes attr) {
        try {
            eServ.modifyEditorial(id, name);
            attr.addFlashAttribute("done", "Se modifico correctamente.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/editorial");
    }

    @GetMapping("/unsubscribe/{id}")
    public RedirectView unsubscribe(@PathVariable String id, RedirectAttributes attr) {
        try {
            eServ.unsusbcribe(id);
            attr.addFlashAttribute("done", "Se dio de baja.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/editorial");
    }

    @GetMapping("/subscribe/{id}")
    public RedirectView subscribe(@PathVariable String id, RedirectAttributes attr) {
        try {
            eServ.susbcribe(id);
            attr.addFlashAttribute("done", "Se dio de alta.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/editorial");
    }
}
