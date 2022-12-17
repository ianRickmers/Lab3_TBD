package com.app.voluntariosbe.services;

import java.util.ArrayList;

import org.bson.Document;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.voluntariosbe.repositories.EmergencyRepository;



@RestController
@CrossOrigin
@RequestMapping(value = "/emergencies")
public class EmergencyMongoService {
    @Autowired
    private EmergencyRepository emergencyRepository;

    @GetMapping(value = "/all/{id}")
    public ArrayList<Document> getAllEmergencies(@PathVariable("id") Integer id) {
        return emergencyRepository.getAllEmergencies(id);
    }

    @GetMapping(value = "/locations")
    public ArrayList<Document> getEmergencyLocations() {
        return emergencyRepository.getEmergencyLocations();
    }
    
}
