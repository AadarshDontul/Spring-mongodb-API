package com.ethane3.springmongodb.service;

import com.ethane3.springmongodb.collection.Person;
import com.ethane3.springmongodb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;


    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonID();
    }
}
