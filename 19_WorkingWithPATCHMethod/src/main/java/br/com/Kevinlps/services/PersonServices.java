package br.com.Kevinlps.services;

import br.com.Kevinlps.controllers.PersonController;
import br.com.Kevinlps.data.dto.PersonDTO;
import br.com.Kevinlps.exception.RequiredObjectIsNullException;
import br.com.Kevinlps.exception.ResourceNotFoundException;
import br.com.Kevinlps.model.Person;
import br.com.Kevinlps.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Service;


import java.util.List;


import static br.com.Kevinlps.mapper.ObjctMapper.parseListObjects;
import static br.com.Kevinlps.mapper.ObjctMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;


    public List<PersonDTO> findAll() {

        logger.info("Finding all People!");

        var persons = parseListObjects(repository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLink);
        return persons;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new RequiredObjectIsNullException("No records found for this ID!"));
        var dto =  parseObject(entity, PersonDTO.class);
        addHateoasLink( dto);
        return dto;
    }



    public PersonDTO create(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);

        var dto  = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLink( dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new RequiredObjectIsNullException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLink( dto);
        return dto;
    }

    public void deleteById(Long id) {
        Person entity = repository.findById(id)
                .orElseThrow(() -> new RequiredObjectIsNullException("No records found for this ID"));
        repository.delete(entity);
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {

        logger.info("Disabling one Person!");

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.disablePerson(id);

        var entity = repository.findById(id).get();
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLink(dto);
        return dto;
    }
    private void addHateoasLink( PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).deleteById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
    }
}
