package edu.egg.Library.service;

import edu.egg.Library.entity.Editorial;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.repository.EditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepository erepo;

    public List<Editorial> EdList() {
        return erepo.findAll();
    }

    public Object findById(String id) {
        return erepo.findById(id);
    }

    public void delete(String id) throws LibraryException {
        Optional <Editorial> resp = erepo.findById(id);
        if(resp.isPresent()){
            Editorial edit = resp.get();
            erepo.delete(edit);
        } else {
            throw new LibraryException("No se encontro la editorial");
        }
    }

    public void addEditorial(String name) throws LibraryException {
        if (name == null || name.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un nombre.");
        }
        Editorial edit = new Editorial();
        edit.setName(name);
        edit.setRegister(true);
        erepo.save(edit);
    }

    public void modifyEditorial(String id, String name) throws LibraryException {
        if (name == null || name.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un nombre.");
        }
        Optional<Editorial> resp = erepo.findById(id);
        if(resp.isPresent()) {
            Editorial edit = resp.get();
            edit.setName(name);
            edit.setRegister(true);
            erepo.save(edit);
        }
    }

    @Transactional
    public void unsusbcribe(String id) throws LibraryException {
        Optional<Editorial> resp = erepo.findById(id);
        if(resp.isPresent()){
            Editorial ed = resp.get();
            ed.setRegister(false);
            erepo.save(ed);
        } else {
            throw new LibraryException("No se encontró la editorial solicitada.");
        }
    }

    @Transactional
    public void susbcribe(String id) throws LibraryException {
        Optional<Editorial> resp = erepo.findById(id);
        if(resp.isPresent()){
            Editorial ed = resp.get();
            ed.setRegister(true);
            erepo.save(ed);
        } else {
            throw new LibraryException("No se encontró la editorial solicitada");
        }
    }
}
