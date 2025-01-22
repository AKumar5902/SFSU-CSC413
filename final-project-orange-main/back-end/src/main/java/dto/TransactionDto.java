package dto;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.Instant;

public class TransactionDto extends BaseDto {

    // STATES
    private String userId;
    private String toId;
    private Double amount;
    private TransactionType transactionType;
    private Long timestamp;

    // CONSTRUCTORS
    public TransactionDto() {
        timestamp = Instant.now().toEpochMilli();
    }

    public TransactionDto(String uniqueId) {
        super(uniqueId);
        timestamp = Instant.now().toEpochMilli();
    }

    // METHODS
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Document toDocument() {
        return new Document("userId", userId)
            .append("toId", toId)
            .append("amount", amount)
            .append("transactionType", transactionType.toString());
    }

    // FUNCTIONS
    public static TransactionDto fromDocument(Document document) {
        var result = new TransactionDto();

        result.setUserId(document.getString("userId"));
        result.setToId(document.getString("toId"));
        result.setAmount(document.getDouble("amount"));
        result.setTransactionType(TransactionType.valueOf(document.getString("transactionType")));

        var id = document.get("_id", ObjectId.class);
        if (id != null) {
            result.setUniqueId(id.toHexString());
        }

        return result;

    }
}
