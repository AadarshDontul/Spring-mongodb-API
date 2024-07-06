package com.ethane3.springmongodb.repository;

import com.ethane3.springmongodb.collection.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo,String> {
    Photo getPhotoBy(String id);

}
