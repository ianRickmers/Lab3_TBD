package com.app.voluntariosbe.repositories;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.*;

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
}

//Consulta 1 en mongo: Tener todas las tareas activas de una emergencia
/*  
db.emergencia.aggregate([
  {
    $lookup: {
      from: "tarea",
      let: { e: "$_id" },
      pipeline: [
        {
          $match: {
            $expr: {
              $and: [
                { $eq: ["$id_emergencia._id", "$$e"] },
                {
                  $or: [
                    {
                      $eq: [
                        "$id_estado.descrip",
                        "Requerida",
                      ],
                    },
                    {
                      $eq: [
                        "$id_estado.descrip",
                        "En proceso",
                      ],
                    },
                  ],
                },
              ],
            },
          },
        },
        {
          $project: {
            estado: "$id_estado.descrip",
            nombre: "$nombre",
          },
        },
      ],
      as: "tareasE",
    },
  },
  {
    $unwind: {
      path: "$tareasE",
    },
  },
  {
    $match: {
      _id: 1, // ID DE LA EMERGENCIA
    },
  },
])
*/