package com.app.voluntariosbe.services;
import java.util.ArrayList;

import org.bson.Document;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/all")
    public ArrayList<Document> getAllEmergencies() {
        return emergencyRepository.getAllEmergencies();
    }
}
