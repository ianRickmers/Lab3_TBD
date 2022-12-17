package com.app.voluntariosbe.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.bson.Document;

import com.app.voluntariosbe.repositories.VolunteerRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/volunteers")
public class VolunteerMongoService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @GetMapping(value = "/near/{lon}/{lat}")
    public ArrayList<Document> getAllVolunteers(@PathVariable("lon") Double longitud, @PathVariable("lat") Double latitud) {
        return volunteerRepository.getVolunteers(longitud, latitud);
    }

}
