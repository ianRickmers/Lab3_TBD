package com.app.voluntariosbe.repositories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import org.bson.Document;

@Repository
public interface EmergencyRepository {

    public ArrayList<Document> getAllEmergencies(Integer id);

    public ArrayList<Document> getEmergencyLocations();

    public Document getEmergencyLocationById(Integer id);

}
