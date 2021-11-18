package edu.egg.Library.service;

import edu.egg.Library.entity.Book;
import edu.egg.Library.entity.Borrow;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.repository.BookRepository;
import edu.egg.Library.repository.BorrowRepository;
import edu.egg.Library.repository.CustomerRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository bRepo;
    @Autowired
    private CustomerRepository cRepo;
    @Autowired
    private BookRepository bookRepo;

    @Transactional
    public void create(String idBook, String idCustomer) throws LibraryException {
        validate(idBook, idCustomer);
        if (bookRepo.findById(idBook).get().getExampleRemain() < 1) {
            throw new LibraryException("No hay libros disponibles.");
        }
        Borrow bor = new Borrow();
        Book book = bookRepo.findById(idBook).get();
        book.setExampleRemain(book.getExampleRemain() - 1);
        book.setExampleBorrow(book.getExampleBorrow() + 1);
        Date borrowDate = new Date();
        bor.setBorrowDate(borrowDate);
        Calendar c = Calendar.getInstance();
        c.setTime(borrowDate);
        c.add(Calendar.DATE, 20);
        bor.setDevDate(borrowDate = c.getTime());
        bor.setBook(bookRepo.findById(idBook).get());
        bor.setCustomer(cRepo.findById(idCustomer).get());
        bookRepo.save(book);
        bRepo.save(bor);

    }

    @Transactional(readOnly = true)
    public Optional<Borrow> findById(String id) {
        return bRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Borrow> show() {
        return bRepo.findAll();
    }

    @Transactional
    public void unsuscribe(String id) throws LibraryException {
        Optional<Borrow> resp = bRepo.findById(id);
        if (resp.isPresent()) {
            Borrow bor = resp.get();
            if (new Date().before(bor.getDevDate())) {
                bor.getCustomer().setRegister(false);
            }
        } else {
            throw new LibraryException("No se encontró el prestamo/cliente");
        }
    }

    @Transactional
    public void delete(String id) throws LibraryException {
        Optional<Borrow> resp = bRepo.findById(id);
        if (resp.isPresent()) {
            Borrow bor = resp.get();
            bRepo.delete(bor);
            bor.getBook().setExampleBorrow(bor.getBook().getExampleBorrow() - 1);
            bor.getBook().setExampleRemain(bor.getBook().getExampleRemain() + 1);

        } else {
            throw new LibraryException("No se encontró el prestamo solicitado.");
        }
    }

    @Transactional
    public void modify(String id, String idBook, String idCustomer) throws LibraryException {
        Optional<Borrow> resp = bRepo.findById(id);
        if (resp.isPresent()) {
            Borrow bor = resp.get();
            Date borrowDate = new Date();
            bor.setBorrowDate(borrowDate);
            Calendar c = Calendar.getInstance();
            c.setTime(borrowDate);
            c.add(Calendar.DATE, 20);
            bor.setDevDate(borrowDate = c.getTime());
            bor.setBook(bookRepo.findById(idBook).get());
            bor.setCustomer(cRepo.findById(idCustomer).get());
            bRepo.save(bor);
        } else {
            throw new LibraryException("No se encontró el prestamo solicitado.");
        }
    }

    @Transactional
    private void validate(String idBook, String idCustomer) throws LibraryException {
        if (idBook == null || idBook.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un ID.");
        }
        if (idCustomer == null || idCustomer.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un ID.");
        }
    }
}
