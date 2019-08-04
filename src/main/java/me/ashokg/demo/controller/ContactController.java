package me.ashokg.demo.controller;

import me.ashokg.demo.Contact;
import me.ashokg.demo.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/contacts")
    public List<Contact> retrieveAllContacts() {
        return contactRepository.findAll();
    }

    @GetMapping("/contacts/{id}")
    public Contact retrieveContact(@PathVariable long id){
        Optional<Contact> contact = contactRepository.findById(id);

        if(!contact.isPresent()){
            throw new DataRetrievalFailureException("id-" + id);
        }

        return contact.get();
    }

    @DeleteMapping("/contacts/{id}")
    public void deleteContact(@PathVariable long id) {
        contactRepository.deleteById(id);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Object> updateContact(@RequestBody Contact contact, @PathVariable long id) {

        Optional<Contact> contactOptional = contactRepository.findById(id);

        if (!contactOptional.isPresent())
            return ResponseEntity.notFound().build();

        contact.setId(id);

        contactRepository.save(contact);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/contacts")
    public ResponseEntity<Object> createContact(@RequestBody Contact contact) {
        Contact savedContact = contactRepository.save(contact);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedContact.getId()).toUri();

        return ResponseEntity.created(location).build();

    }
}
