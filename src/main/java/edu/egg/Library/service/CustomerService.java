package edu.egg.Library.service;

import edu.egg.Library.entity.Customer;
import edu.egg.Library.exception.LibraryException;
import edu.egg.Library.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository cRepo;

    @Transactional
    public void addCustomer(String name, String lastName, String phone, Long dni) throws LibraryException {
        validate(name, lastName, phone, dni);

        Customer cus = new Customer();
        cus.setDni(dni);
        cus.setName(name);
        cus.setLastName(lastName);
        cus.setPhoneNumber(phone);
        cus.setRegister(true);
        cRepo.save(cus);
    }

    @Transactional(readOnly = true)
    public List<Customer> show() {
        return cRepo.findAll();
    }

    public void delete(String id) throws LibraryException {
        Optional<Customer> resp = cRepo.findById(id);
        if (resp.isPresent()) {
            Customer cus = resp.get();
            cRepo.delete(cus);
        } else {
            throw new LibraryException("No se encontró el cliente solicitado");
        }
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findByID(String id) {
        return cRepo.findById(id);
    }

    @Transactional
    public void modify(String id, String name, String lastName, String phone, Long dni) throws LibraryException {
        validate(name, lastName, phone, dni);
        Optional<Customer> resp = cRepo.findById(id);
        if (resp.isPresent()) {
            Customer cus = resp.get();
            cus.setDni(dni);
            cus.setName(name);
            cus.setLastName(lastName);
            cus.setPhoneNumber(phone);
            cus.setRegister(true);
            cRepo.save(cus);
        }
    }

    @Transactional
    public void unsusbcribe(String id) throws LibraryException {
        Optional<Customer> resp = cRepo.findById(id);
        if (resp.isPresent()) {
            Customer cus = resp.get();
            cus.setRegister(false);
            cRepo.save(cus);
        } else {
            throw new LibraryException("No se encontró el cliente solicitado.");
        }
    }

    @Transactional
    public void susbcribe(String id) throws LibraryException {
        Optional<Customer> resp = cRepo.findById(id);
        if (resp.isPresent()) {
            Customer cus = resp.get();
            cus.setRegister(true);
            cRepo.save(cus);
        } else {
            throw new LibraryException("No se encontró el cliente solicitado.");
        }
    }

    private void validate(String name, String lastName, String phone, Long dni) throws LibraryException {

        if (name == null || name.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un nombre.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un apellido.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new LibraryException("Debe ingresar un numero de telefono.");
        }
        if (dni == null || dni < 0) {
            throw new LibraryException("Debe ingresar su dni.");
        }
    }

}
