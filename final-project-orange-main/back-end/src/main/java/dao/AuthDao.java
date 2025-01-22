package dao;

import com.mongodb.client.MongoCollection;
import dto.AuthDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;

public class AuthDao extends BaseDao<AuthDto> {
    // STATES
    private static AuthDao instance;

    // CONSTRUCTORS
    private AuthDao(MongoCollection<Document> collection) {
        super(collection);
    }

    // FUNCTIONS
    public static AuthDao getInstance() {
        if (instance != null) return instance;
        
        return instance = new AuthDao(MongoConnection.getCollection("AuthDao"));
    }

    public static AuthDao getInstance(MongoCollection<Document> collection) {
        return instance = new AuthDao(collection);
    }

    // METHODS
    @Override
    public List<AuthDto> query(Bson filter) {
        return collection.find(filter)
            .into(new ArrayList<>())
            .stream()
            .map(AuthDto::fromDocument)
            .collect(Collectors.toList());
    }
}