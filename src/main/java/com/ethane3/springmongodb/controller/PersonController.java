package com.ethane3.springmongodb.controller;

import com.ethane3.springmongodb.collection.Person;
import com.ethane3.springmongodb.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    private String save(@RequestBody Person person){
        return personService.save(person);
    }

    @GetMapping
    private List<Person> getPersonStartWith(@RequestParam("name") String name){
        return personService.getPersonStartWith(name);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable String id){
        personService.delete(id);
    }

    @GetMapping("/age")
    private List<Person> getByPersonAge(@RequestParam Integer minAge,
                                        @RequestParam Integer maxAge){
        return personService.getByPersonAge(minAge,maxAge);
    }

    @GetMapping("/search")
    private Page<Person> searchPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    )
    {
        Pageable pageable = PageRequest.of(page,size);
        return personService.search(name,minAge,maxAge,city,pageable);

    }

    @GetMapping("/oldestPerson")
    private List<Document> getOldestPerson(){
        return personService.getOldesPersonByCity();
    }


}
