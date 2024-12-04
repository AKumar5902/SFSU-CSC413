package dto;

import org.bson.Document;

public class UserDto extends BaseDto {

    private String userName;
    private String password;
    private Double balance = 0.0d;

    public UserDto() {
        super();
    }

    public UserDto(String uniqueId) {
        super(uniqueId);
    }

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

        var doc = new Document();
        doc.append("uniqueId", uniqueId);
        doc.append("userName", userName);
        doc.append("password", password);
        doc.append("balance", balance);
        return doc;
    }

    public static UserDto fromDocument(Document match) {
        var user= new UserDto();
        user.setUniqueId(match.getString("uniqueId"));
        user.setUserName(match.getString("userName"));
        user.setPassword(match.getString("password"));
        user.setBalance(match.getDouble("balance"));
        return user;

    }
}
