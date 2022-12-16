package com.app.voluntariosbe.repositories;

import com.app.voluntariosbe.models.EmergencyMongo;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public interface EmergencyMongoRepository {
    public List<Document> getEmergencias();
}
