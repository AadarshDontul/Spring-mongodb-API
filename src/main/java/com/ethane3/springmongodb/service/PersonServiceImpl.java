package com.ethane3.springmongodb.service;

import com.ethane3.springmongodb.collection.Person;
import com.ethane3.springmongodb.repository.PersonRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonID();
    }

    @Override
    public List<Person> getPersonStartWith(String name) {
        return personRepository.findByFirstNameStartsWith(name);
    }

    @Override
    public void delete(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getByPersonAge(Integer minAge, Integer maxAge) {
        return personRepository.findByPersonAge(minAge,maxAge);
    }

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {

        Query query = new Query().with(pageable);

        List<Criteria> criteria = new ArrayList<>();

        if(name !=null && !name.isEmpty()){
            criteria.add(Criteria.where("firstname").regex(name,"i"));
        }

        if(minAge!=null && maxAge!=null){
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }

        if(city!=null && !city.isEmpty()){
            criteria.add(Criteria.where("address.city").is(city));
        }

        if(!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        Page<Person> people = PageableExecutionUtils.getPage(
                mongoTemplate.find(query,Person.class),
                pageable,
                ()-> mongoTemplate.count(query.skip(0).limit(0),Person.class)
        );

        return people;

    }

    @Override
    public List<Document> getOldesPersonByCity() {

        UnwindOperation unwindOperation
                = Aggregation.unwind("address");
        SortOperation sortOperation
                = Aggregation.sort(Sort.Direction.DESC,"age");
        GroupOperation groupOperation
                = Aggregation.group("address.city")
                .first(Aggregation.ROOT)
                .as("asOldestPerson");
        Aggregation aggregation
                = Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation);

        return mongoTemplate.aggregate(aggregation,Person.class,Document.class).getMappedResults();
    }

    @Override
    public List<Document> getPopulationByCity() {

        UnwindOperation unwindOperation
                = Aggregation.unwind("address");
        GroupOperation groupOperation
                = Aggregation.group("address.city")
                .count().as("popCount");
        SortOperation sortOperation
                = Aggregation.sort(Sort.Direction.DESC,"popCount");

        ProjectionOperation projectionOperation
                = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("popCount").as("count")
                .andExclude("_id");

        Aggregation aggregation
                = Aggregation.newAggregation(unwindOperation,groupOperation,sortOperation,projectionOperation);



        return mongoTemplate.aggregate(aggregation,Person.class,Document.class).getMappedResults();
    }


}
