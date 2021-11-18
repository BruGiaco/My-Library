package edu.egg.Library.controller;

import edu.egg.Library.entity.Book;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.service.AuthorService;
import edu.egg.Library.service.BookService;
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
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bServ;
    @Autowired
    private AuthorService aServ;
    @Autowired
    private EditorialService eServ;

    @GetMapping
    public ModelAndView showBooks(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("book");
        List<Book> listBooks = bServ.ListBooks();

        Map<String, ?> flasMap = RequestContextUtils.getInputFlashMap(request);
        if (flasMap != null) {
            mav.addObject("done", flasMap.get("done"));
            mav.addObject("error", flasMap.get("error"));
        }
        mav.addObject("books", listBooks);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createBook() {
        ModelAndView mav = new ModelAndView("BookForm");
        mav.addObject("book", new Book());
        mav.addObject("title", "Create Book");
        mav.addObject("action", "save");
        mav.addObject("authors", aServ.show());
        mav.addObject("editorials", eServ.EdList());
        return mav;
    }

    @GetMapping("/unsubscribe/{id}")
    public RedirectView unsubscribe(@PathVariable String id, RedirectAttributes attr) {
        try {
            bServ.unsusbcribe(id);
            attr.addFlashAttribute("done", "Se dio de baja.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/book");
    }

    @GetMapping("/subscribe/{id}")
    public RedirectView subscribe(@PathVariable String id, RedirectAttributes attr) {
        try {
            bServ.susbcribe(id);
            attr.addFlashAttribute("done", "Se dio de alta.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/book");
    }

    @PostMapping("/save")
    public RedirectView saveBook(@RequestParam Long isbn, @RequestParam String title, @RequestParam Integer pubDate, @RequestParam Integer example, @RequestParam String author, @RequestParam String editorial, RedirectAttributes attr) throws LibraryException {
        try {
            bServ.add(isbn, title, pubDate, example, author, editorial);
            attr.addFlashAttribute("done", "Se agregó correctamente el libro.");

        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/book");
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modifyBook(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("BookForm");
        mav.addObject("book", bServ.findById(id));
        mav.addObject("title", "Modify book");
        mav.addObject("action", "edit");
        mav.addObject("authors", aServ.show());
        mav.addObject("editorials", eServ.EdList());
        return mav;
    }

    @PostMapping("/edit")
    public RedirectView editBook(@RequestParam String id, @RequestParam Long isbn, @RequestParam String title, @RequestParam Integer pubDate, @RequestParam Integer example, @RequestParam String author, @RequestParam String editorial, RedirectAttributes attr) throws LibraryException {
        try {
            bServ.modify(isbn, id, title, pubDate, example, author, editorial);
            attr.addFlashAttribute("done", "Se modificó correctamente.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/book");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") String id, RedirectAttributes attr) throws LibraryException {
        try {
            bServ.deleteBook(id);
            attr.addFlashAttribute("done", "Se eliminó correctamente el libro.");
        } catch (LibraryException e) {
            attr.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/book");
    }
}
