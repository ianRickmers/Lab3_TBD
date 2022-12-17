package com.app.voluntariosbe.repositories;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import org.bson.conversions.Bson;

@Repository
public class EmergencyRepositoryImp implements EmergencyRepository {
    @Override
    public ArrayList<Document> getAllEmergencies(Integer id) {
        ArrayList<Document> emergencias = new ArrayList<Document>();
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/voluntarios");
        MongoDatabase database = mongoClient.getDatabase("voluntarios");
        MongoCollection<Document> collection = database.getCollection("emergencia");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$lookup",
                new Document("from", "tarea")
                        .append("let",
                                new Document("e", "$_id"))
                        .append("pipeline", Arrays.asList(new Document("$match",
                                new Document("$expr",
                                        new Document("$and",
                                                Arrays.asList(
                                                        new Document("$eq", Arrays.asList("$id_emergencia._id", "$$e")),
                                                        new Document("$or",
                                                                Arrays.asList(
                                                                        new Document("$eq",
                                                                                Arrays.asList("$id_estado.descrip",
                                                                                        "Requerida")),
                                                                        new Document("$eq",
                                                                                Arrays.asList("$id_estado.descrip",
                                                                                        "En proceso")))))))),
                                new Document("$project",
                                        new Document("estado", "$id_estado.descrip")
                                                .append("nombre", "$nombre"))))
                        .append("as", "tareasE")),
                new Document("$unwind",
                        new Document("path", "$tareasE")),
                new Document("$match",
                        new Document("_id", id))));
        for (Document doc : result) {
            emergencias.add(doc);
        }
        return emergencias;
    }

    public ArrayList<Document> getEmergencyLocations() {
        ArrayList<Document> emergencias = new ArrayList<Document>();
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/voluntarios");
        MongoDatabase database = mongoClient.getDatabase("voluntarios");
        MongoCollection<Document> collection = database.getCollection("emergencia");
        Bson filter = new Document();
        FindIterable<Document> result = collection.find(filter);
        for (Document doc : result) {
            emergencias.add(doc);
        }
        return emergencias;
    }

    public Document getEmergencyLocationById(Integer id) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/voluntarios");
        MongoDatabase database = mongoClient.getDatabase("voluntarios");
        MongoCollection<Document> collection = database.getCollection("emergencia");
        Bson filter = eq("_id", id);
        Bson project = and(eq("_id", 0L), eq("coordenadas", "$geom.coordinates"));

        FindIterable<Document> result = collection.find(filter)
                .projection(project);
        Document emergencia = result.first();
        return emergencia;
    }
}
