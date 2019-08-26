package br.com.javaapirestful.conn;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoDB {

    private String uri;
    private String databaseName;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private static MongoDB instance;

    private MongoDB() {

        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(getClass().
                    getResource("/br/com/javaapirestful/conn/params.json"));

            uri = node.get("mongodb").path("uri").asText();
            databaseName = node.get("mongodb").path("database").asText();

            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));

            MongoClientURI mongoClientURI = new MongoClientURI(
                    uri);

            mongoClient = new MongoClient(mongoClientURI);
            mongoDatabase = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);

        } catch (Exception ex) {
        }

    }

    public static MongoDB getInstance() {
        if (instance == null) {
            instance = new MongoDB();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return mongoDatabase;

    }

}
