package dto;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
///import org.bson.types.ObjectId;
import java.sql.Timestamp;
import java.time.Instant;

public class AuthDto extends BaseDto {

    // STATES
    private String userName;
    private Long expireTime;
    private String hash;

    // METHODS
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getUserName() {
        return userName;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public Document toDocument() {
        var doc = new Document();
        doc.append("userName", userName);
        doc.append("expireTime", expireTime);
        doc.append("hash", hash);
        return doc;
    }

    // FUNCTIONS
    public static AuthDto fromDocument(Document document) {
        var auth = new AuthDto();
        auth.setExpireTime(document.getLong("expireTime"));
        auth.setUserName(document.getString("userName"));
        auth.setHash(document.getString("hash"));
        return auth;
    }

    /**
     * Current function responsible for generating a cookie
     * @param userName
     * @return
     */
    public static String getCookie(String userName){
        return DigestUtils.sha256Hex(userName + Timestamp.from(Instant.now()));
    }
}
