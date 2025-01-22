package dao;

import com.mongodb.client.MongoCollection;
import dto.MessageDto;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MessageDao extends BaseDao<MessageDto> {
    private static MessageDao instance;

    // CONSTRUCTORS
    private MessageDao(MongoCollection<Document> collection) {
        super(collection);
    }

    // FUNCTIONS
    public static MessageDao getInstance() {
        if (instance != null) return instance;

        return instance = new MessageDao(MongoConnection.getCollection("MessageDao"));
    }

    public static MessageDao getInstance(MongoCollection<Document> collection) {
        return instance = new MessageDao(collection);
    }

    @Override
    List<MessageDto> query(Bson filter) {
        return this.collection.find(filter)
                .into(new ArrayList<Document>())
                .stream()
                .map(MessageDto::fromDocument)
                .toList();
    }

}
