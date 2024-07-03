package com.ethane3.springmongodb.controller;

import com.ethane3.springmongodb.collection.Person;
import com.ethane3.springmongodb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    private String save(@RequestBody Person person){
        return personService.save(person);
    }
}
