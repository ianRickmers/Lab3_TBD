package com.app.voluntariosbe.services;
import java.util.ArrayList;

import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.voluntariosbe.repositories.EmergencyRepository;


@Service
@RequestMapping(value = "/emergencies")
public class EmergencyMongoService {
    @Autowired
    private EmergencyRepository emergencyRepository;

    @GetMapping(value = "/all")
    public ArrayList<Document> getAllEmergencies() {
        return emergencyRepository.getAllEmergencies();
    }
}
