package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCollection;

import dto.ContactDto;

public class ContactDao extends BaseDao<ContactDto>{

	// STATES
	private static ContactDao instance;

	// CONSTRUCTORS
	private ContactDao(MongoCollection<Document> collection) {
        super(collection);
    }

	public static ContactDao getInstance() {
        if (instance != null) return instance;
        
        return instance = new ContactDao(MongoConnection.getCollection("ContactDto"));
    }

    public static ContactDao getInstance(MongoCollection<Document> collection) {
        return instance = new ContactDao(collection);
    }

	@Override
	public List<ContactDto> query(Bson bson) {
		return collection.find(bson)
            .into(new ArrayList<>())
            .stream()
            .map(ContactDto::fromDocument)
            .collect(Collectors.toList());
	}
	
}
