package com.ethane3.springmongodb.collection;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document
public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private Integer age;
    List<String> hobbies;
    List<Address> address;
}
