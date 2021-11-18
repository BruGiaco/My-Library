package edu.egg.Library.service;

import edu.egg.Library.entity.Author;
import edu.egg.Library.entity.Book;
import edu.egg.Library.entity.Editorial;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.repository.AuthorRepository;
import edu.egg.Library.repository.BookRepository;
import edu.egg.Library.repository.EditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository brepo;
    @Autowired
    private EditorialRepository erepo;
    @Autowired
    private AuthorRepository arepo;

    @Transactional
    public void add(long isbn, String title, Integer pubDate, Integer example, String idAuthor, String idEditorial) throws LibraryException {

        
        validate(isbn, title, pubDate, example);
        Book book = new Book();

        book.setIsbn(isbn);
        book.setTitle(title);
        book.setPubDate(pubDate);
        book.setExample(example);
        book.setExampleBorrow(0);
        book.setExampleRemain(example);
        book.setRegister(true);
        book.setAuthor(arepo.findById(idAuthor).get());
        book.setEditorial(erepo.findById(idEditorial).get());
        brepo.save(book);
    }

    @Transactional
    public void modify(long isbn, String id, String title, Integer pubDate, Integer example, String author, String edit) throws LibraryException {
        validate(isbn, title, pubDate, example);
        Optional<Book> resp = brepo.findById(id);
        if(resp.isPresent()){
            Book book = resp.get();
            book.setTitle(title);
            book.setPubDate(pubDate);
            book.setIsbn(isbn);
            book.setExample(example);
            book.setEditorial(erepo.findById(edit).get());
            book.setAuthor(arepo.findById(author).get());
            brepo.save(book);
        } else {
            throw new LibraryException("No se encontró el libro que busca.");
        }
    }
    
    @Transactional
    public void unsusbcribe(String id) throws LibraryException {
        Optional<Book> resp = brepo.findById(id);
        if(resp.isPresent()){
            Book book = resp.get();
            book.setRegister(false);
            brepo.save(book);
        } else {
            throw new LibraryException("No se encontró el libro solicitado");
        }
    }

    @Transactional
    public void susbcribe(String id) throws LibraryException {
        Optional<Book> resp = brepo.findById(id);
        if(resp.isPresent()){
            Book book = resp.get();
            book.setRegister(true);
            brepo.save(book);
        } else {
            throw new LibraryException("No se encontró el libro solicitado");
        }
    }
    @Transactional
    public void deleteBook(String id) throws LibraryException {
        Optional<Book> response = brepo.findById(id);
        if(response.isPresent()){
            Book book = response.get();
            brepo.delete(book);
        }else{
            throw new LibraryException("No se encontro el libro");
        }
    }
    
    private void validate(long isbn, String title, Integer pubDate, Integer example) throws LibraryException {
        if (isbn <= 0) {
            throw new LibraryException("El codigo ISBN debe ser mayor a 0");
        }
        if (title == null || title.isEmpty()) {
            throw new LibraryException("Debe ingresar un titulo valido.");
        }
        if (pubDate == null) {
            throw new LibraryException("Debe ingresar una fecha de publicacion.");
        }
        if (example <= 0) {
            throw new LibraryException("Debe ingresar ejemplares a almacenar.");
        }
    }

    public List<Book> ListBooks() {
        return brepo.findAll();
    }

    public Object findById(String id) {
        return brepo.findById(id);
    }
}
