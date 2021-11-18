package edu.egg.Library.controller;

import edu.egg.Library.entity.Author;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.service.AuthorService;
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
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService aServ;

    @GetMapping
    public ModelAndView showAuthor(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("author");
        List<Author> list = aServ.show();
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("done", flashMap.get("done"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("authors", list);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("AuthorForm");
        mav.addObject("author", new Author());
        mav.addObject("title", "create author");
        mav.addObject("action", "save");

        return mav;
    }

    @PostMapping("/save")
    public RedirectView saveEditorial(@RequestParam String name, RedirectAttributes attr) {
        try {
            aServ.addAuthor(name);
            attr.addFlashAttribute("done", "Se agregó correctamente");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/author");
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("AuthorForm");
        mav.addObject("author", aServ.findById(id));
        mav.addObject("title", "modify author");
        mav.addObject("action", "edit");

        return mav;
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable String id, RedirectAttributes attr) throws LibraryException {
        try {
            aServ.delete(id);
            attr.addFlashAttribute("done", "Se eliminó el autor");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/author");
    }

    @PostMapping("/edit")
    public RedirectView EditAuthor(@RequestParam String id, @RequestParam String name, RedirectAttributes attr) {
        try {
            aServ.modify(id, name);
            attr.addFlashAttribute("done", "Se modificó el autor");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/author");
    }

    @GetMapping("/unsubscribe/{id}")
    public RedirectView unsubscribe(@PathVariable String id, RedirectAttributes attr) {
        try {
            aServ.unsusbcribe(id);
            attr.addFlashAttribute("done", "Se dio de baja.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/author");
    }

    @GetMapping("/subscribe/{id}")
    public RedirectView subscribe(@PathVariable String id, RedirectAttributes attr) {
        try {
            aServ.susbcribe(id);
            attr.addFlashAttribute("done", "Se dio de alta.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/author");
    }
}
