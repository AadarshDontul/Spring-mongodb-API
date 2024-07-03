package com.ethane3.springmongodb.service;

import com.ethane3.springmongodb.collection.Person;
import org.springframework.stereotype.Component;

public interface PersonService {
    String save(Person person);
}
