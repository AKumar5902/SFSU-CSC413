package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dto.BaseDto;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public abstract class BaseDao<T extends BaseDto> {
    // STATES
    final MongoCollection<Document> collection;

    // CONSTRUCTORS
    protected BaseDao(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    // METHODS
    //abstract List<T> query(Document filter);
    abstract List<T> query(Bson bson);

    /**
     * Deletes one document in mongo with a unique id
     * @param dto
     * @return
     */
    public boolean delete(T dto){
        var id = dto.getUniqueId();
        return id != null && null !=
            collection.findOneAndDelete(
                Filters.eq("_id", new ObjectId(id))
            );
    };

    public void put(T dto) {
        if (dto.getUniqueId() == null) {
            collection.insertOne(dto.toDocument());
        }
        else {
            collection.replaceOne(dto.getObjectId(), dto.toDocument());
        }
    }

}