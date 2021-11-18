package edu.egg.Library.controller;

import edu.egg.Library.entity.Borrow;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.repository.BorrowRepository;
import edu.egg.Library.service.BookService;
import edu.egg.Library.service.BorrowService;
import edu.egg.Library.service.CustomerService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService bServ;
    @Autowired
    private BookService bookServ;
    @Autowired
    private CustomerService cServ;

    @GetMapping
    public ModelAndView showBorrows(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("borrow");
        List<Borrow> listBorrow = bServ.show();

        Map<String, ?> flasMap = RequestContextUtils.getInputFlashMap(request);
        if (flasMap != null) {
            mav.addObject("done", flasMap.get("done"));
            mav.addObject("error", flasMap.get("error"));
        }
        mav.addObject("borrows", listBorrow);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createBorrow() {
        ModelAndView mav = new ModelAndView("BorrowForm");
        mav.addObject("borrow", new Borrow());
        mav.addObject("title", "Create Borrow");
        mav.addObject("action", "save");
        mav.addObject("books", bookServ.ListBooks());
        mav.addObject("customers", cServ.show());
        return mav;
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modifyBorrow(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("BorrowForm");
        mav.addObject("borrow", bServ.findById(id));
        mav.addObject("title", "Modify book");
        mav.addObject("action", "edit");
        mav.addObject("books", bookServ.ListBooks());
        mav.addObject("customers", cServ.show());
        return mav;
    }
    @PostMapping("/save")
    public RedirectView saveBorrow( @RequestParam String book, @RequestParam String customer, RedirectAttributes attr) throws LibraryException {
        try {
            bServ.create(book, customer);
            attr.addFlashAttribute("done", "Se agregó correctamente.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/borrow");
    }
    
    @PostMapping("/edit")
    public RedirectView editBorrow(@RequestParam String id, @RequestParam String book, @RequestParam String customer, RedirectAttributes attr) throws LibraryException {
        try {
            bServ.modify(id, book, customer);
            attr.addFlashAttribute("done", "Se modificó correctamente.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/borrow");
    }
    
    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") String id, RedirectAttributes attr) throws LibraryException {
        try {
            bServ.delete(id);
            attr.addFlashAttribute("done", "Se eliminó correctamente.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/borrow");
    }
}
