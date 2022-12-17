package com.app.voluntariosbe.repositories;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import org.bson.Document;

@Repository
public interface VolunteerRepository {

    ArrayList<Document> getVolunteers(Double longitud, Double latitud);
    
}