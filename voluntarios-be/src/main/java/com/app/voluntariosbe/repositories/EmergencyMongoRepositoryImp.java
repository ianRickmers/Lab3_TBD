package com.app.voluntariosbe.repositories;

import com.app.voluntariosbe.models.EmergencyMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
public class EmergencyMongoRepositoryImp implements EmergencyMongoRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Document> getEmergencias() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017/"));
        MongoDatabase database = mongoClient.getDatabase("voluntarios");
        MongoCollection<Document> collection = database.getCollection("emergencia");
        List <Document> emergencias = collection.find().into(new ArrayList<>());
        
        return emergencias;
    }
}
