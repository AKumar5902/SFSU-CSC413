package dto;

import dao.BaseDao;
import org.bson.Document;
import org.bson.types.ObjectId;


public class MessageDto extends BaseDto {

    //States
    public String message;
    public String fromId;
    public String toId;

    //Constructors

    public MessageDto() {

    }

    public MessageDto(String uniqueId) {
        super(uniqueId);
    }

    //Methods
    public String getUserId() {
        return fromId;
    }

    public void setUserId(String userId) {
        this.fromId = userId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Document toDocument() {
        return new Document("userId", fromId)
                .append("toId", toId)
                .append("message", message);
    }

    // FUNCTIONS
    public static MessageDto fromDocument(Document document) {
        var result = new MessageDto();

        result.setUserId(document.getString("userId"));
        result.setToId(document.getString("toId"));
        result.setMessage(document.getString("message"));

        var id = document.get("_id", ObjectId.class);
        if (id != null) {
            result.setUniqueId(id.toHexString());
        }

        return result;

    }
}
