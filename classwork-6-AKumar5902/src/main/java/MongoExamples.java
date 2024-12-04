import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoExamples {

    public static void main(String args[]) {
        // open connection
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        // get ref to database
        MongoDatabase db = mongoClient.getDatabase("CSC413");
        // get ref to collection
        MongoCollection<Document> myCollection = db.getCollection("Classwork6");
        // create a new document

        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
        // insert document into collection
        myCollection.insertOne(doc);
//
//        // count all documents in collection
        System.out.println("Total Documents :" + myCollection.count());
//
//        // iterate some documents
//        // https://www.mongodb.com/docs/manual/reference/operator/query/
        List<Document> docs = myCollection.find().limit(100).into(new ArrayList<>());
        docs.forEach(myDocuments -> System.out.println(myDocuments));
//
//        // fetching a value from a search
//        // Common filters: https://www.mongodb.com/docs/manual/reference/operator/query-comparison/
        Document search = myCollection.find(Filters.eq("count", 1)).first();
        //System.out.println(search.getString("type"));
        System.out.println("My filter Search");
        System.out.println(search);
//
//        // updating a value
//        // https://www.mongodb.com/docs/manual/reference/operator/update/
        myCollection.updateOne(Filters.eq("name", "hello"), new Document("$set", new Document("name", "Anuj")));
    }
}
