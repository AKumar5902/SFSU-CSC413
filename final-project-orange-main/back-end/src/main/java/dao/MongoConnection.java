package dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnection {

    // STATES
    private static String host = "localhost";
    private static int port = 27017;
    private static MongoClient mongoClient = new MongoClient(host, port);

    // FUNCTIONS
    public static MongoDatabase getDb() {
        return mongoClient.getDatabase("Homework2");
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        return getDb().getCollection(collectionName);
    }

}