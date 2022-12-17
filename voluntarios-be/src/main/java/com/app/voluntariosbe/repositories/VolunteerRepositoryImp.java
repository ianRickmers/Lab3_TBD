package com.app.voluntariosbe.repositories;

import java.util.ArrayList;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.*;
import org.bson.conversions.Bson;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import static com.mongodb.client.model.Filters.near;

@Repository
public class VolunteerRepositoryImp implements VolunteerRepository {

    public ArrayList<Document> getVolunteers(Double longitud, Double latitud) {
        Bson filter = near("geom", new Point(new Position(longitud, latitud)), 500000d, 0d);

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/voluntarios");
        MongoDatabase database = mongoClient.getDatabase("voluntarios");
        MongoCollection<Document> collection = database.getCollection("voluntario");
        FindIterable<Document> result = collection.find(filter);
        ArrayList<Document> voluntarios = new ArrayList<Document>();
        for (Document doc : result) {
            voluntarios.add(doc);
        }
        return voluntarios;
    }
}
