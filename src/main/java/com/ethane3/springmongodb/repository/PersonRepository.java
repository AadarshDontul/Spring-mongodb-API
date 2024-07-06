package com.ethane3.springmongodb.repository;

import com.ethane3.springmongodb.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person,String> {

    List<Person> findByFirstNameStartsWith(String name);

    @Override
    void deleteById(String s);

    //List<Person> findPersonBetweenAge(Integer minAge, Integer maxAge);

    @Query(value = "{'age' : {$gt : ?0,$lt : ?1}}",
            fields = "{address: 0}")
    List<Person> findByPersonAge(Integer min, Integer max);
}
