package edu.egg.Library.service;

import edu.egg.Library.entity.Author;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository arepo;

    @Transactional
    public void addAuthor(String name) throws LibraryException {

        if (name == null || name.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un nombre.");
        }
        Author author = new Author();
        author.setName(name);
        author.setRegister(true);
        arepo.save(author);
    }

    @Transactional
    public void modify(String id, String name) throws LibraryException {
        if (id == null || id.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un nombre.");
        }
        Optional<Author> resp = arepo.findById(id);
        if (resp.isPresent()) {
            Author author = resp.get();
            author.setName(name);
            author.setRegister(true);
            arepo.save(author);
        }
    }

    @Transactional(readOnly = true)
    public List<Author> show() {
        return arepo.findAll();
    }

    @Transactional(readOnly = true)
    public Object findById(String id) {
        return arepo.findById(id);
    }

    @Transactional
    public void delete(String id) throws LibraryException {
        Optional<Author> resp = arepo.findById(id);
        if (resp.isPresent()) {
            Author author = resp.get();
            arepo.delete(author);
        } else {
            throw new LibraryException("No se encontro el autor");
        }
    }
@Transactional
    public void unsusbcribe(String id) throws LibraryException {
        Optional<Author> resp = arepo.findById(id);
        if(resp.isPresent()){
            Author at = resp.get();
            at.setRegister(false);
            arepo.save(at);
        } else {
            throw new LibraryException("No se encontró el autor solicitado.");
        }
    }

    @Transactional
    public void susbcribe(String id) throws LibraryException {
        Optional<Author> resp = arepo.findById(id);
        if(resp.isPresent()){
            Author at = resp.get();
            at.setRegister(true);
            arepo.save(at);
        } else {
            throw new LibraryException("No se encontró el autor solicitado.");
        }
    }
    
}
