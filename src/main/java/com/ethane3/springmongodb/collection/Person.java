package com.ethane3.springmongodb.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Builder
@Document(collection = "person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    @Id
    private String personID;
    private String firstName;
    private String lastName;
    private Integer age;
    List<String> hobbies;
    List<Address> address;
}
