package dao;

import com.mongodb.client.MongoCollection;
import dto.UserDto;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

public class UserDao extends BaseDao<UserDto> {

    // STATES
    private static UserDao instance;

    // CONSTRUCTORS
    private UserDao(MongoCollection<Document> collection) {
        super(collection);
    }

    // FUNCTIONS
    public static UserDao getInstance() {
        if (instance != null) return instance;
        
        return instance = 
            new UserDao(MongoConnection.getCollection("UserDao"));
    }

    public static UserDao getInstance(MongoCollection<Document> collection) {
        return instance = new UserDao(collection);
    }

    // METHODS
    /**
     * Attempts to return a list of users whichs satisfies Bson filter
     * @param filter param
     * @return list, assume ArrayList first
     */
    public List<UserDto> query(Bson filter) {
        return this.collection.find(filter)
            .into( new ArrayList<Document>())
            .stream()
            .map(UserDto::fromDocument)
            .toList();
    }
}