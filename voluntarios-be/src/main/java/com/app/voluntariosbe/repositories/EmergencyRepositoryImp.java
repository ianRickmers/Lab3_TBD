package com.app.voluntariosbe.repositories;

import java.util.ArrayList;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.*;

@Repository
public class EmergencyRepositoryImp implements EmergencyRepository {
    @Override
    public ArrayList<Document> getAllEmergencies() {
        ArrayList<Document> emergencies = new ArrayList<Document>();
        MongoClient mongoClient = MongoClients.create("mongodb://mongo:mongo@localhost:27017/voluntarios");
        MongoDatabase database = mongoClient.getDatabase("voluntarios");
        MongoCollection<Document> collection = database.getCollection("emergencia");
        FindIterable<Document> iterable = collection.find();
        iterable.forEach(emergencies::add);
        return emergencies;
    }
}
