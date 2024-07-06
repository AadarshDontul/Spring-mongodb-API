package com.ethane3.springmongodb.controller;

import com.ethane3.springmongodb.collection.Photo;
import com.ethane3.springmongodb.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @PostMapping
    private String addPhoto(@RequestParam("image")MultipartFile image) throws IOException {
        String id = photoService.addPhoto(image.getOriginalFilename(),image);
        return id;
    }

    @GetMapping("/{id}")
    private ResponseEntity<Resource> getPhotoById(@PathVariable String id){
        Photo photo = photoService.getPhotoBy(id);

        Resource resource = new ByteArrayResource(photo.getPhoto().getData());

        return (ResponseEntity<Resource>) ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+photo.getTitle()+"\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
