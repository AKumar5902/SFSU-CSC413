package dao;

import com.mongodb.client.MongoCollection;

import java.util.ArrayList;
import java.util.List;

import dto.TransactionDto;
import org.bson.Document;
import org.bson.conversions.Bson;

// TODO: fill this out
public class TransactionDao extends BaseDao<TransactionDto> {
    // STATES
    private static TransactionDao instance;

    // CONSTRUCTORS
    private TransactionDao(MongoCollection<Document> collection) {super(collection);}

    // FUNCTIONS
    public static TransactionDao getInstance() {
        if (instance != null) return instance;

        return instance = new TransactionDao(MongoConnection.getCollection("TransactionDao"));
    }

    public static TransactionDao getInstance(MongoCollection<Document> collection) {
        return instance = new TransactionDao(collection);
    }

    // METHODS
    /**
     * TODO:
     * Attempts to return a list of transactions whichs satisfies Document filter
     * @param filter param
     * @return list, assume ArrayList first
     */
    public List<TransactionDto> query(Bson filter) {
        return this.collection.find(filter)
            .into( new ArrayList<Document>())
            .stream()
            .map(TransactionDto::fromDocument)
            .toList();
    }

}