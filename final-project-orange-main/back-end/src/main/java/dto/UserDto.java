package dto;

import org.bson.Document;
import org.bson.types.ObjectId;

public class UserDto extends BaseDto {

    // STATES
    private String userName;
    private String password;
    private Double balance = 0.0d;

    // CONSTRUCTORS
    public UserDto() {
        super();
    }

    public UserDto(String uniqueId) {
        super(uniqueId);
    }

    // METHODS
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Document toDocument() {
        return new Document("userName", userName)
            .append("password", password)
            .append("balance", balance);
    }

    // FUNCTIONS
    public static UserDto fromDocument(Document match) {
        UserDto result = new UserDto();

        result.setBalance(match.get("balance", Double.class));
        result.setUserName(match.get("userName", String.class));
        result.setPassword(match.get("password", String.class));

        var id = match.get("_id", ObjectId.class);
        if (id != null) {
            result.setUniqueId(id.toHexString());
        }

        return result;
    }
}
