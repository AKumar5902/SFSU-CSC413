package handler;

import com.mongodb.lang.NonNull;
import dao.AuthDao;
import dto.AuthDto;
import org.bson.Document;
import request.ParsedRequest;

import java.time.Instant;

public class AuthFilter {
    // FUNCTIONS
    public static class AuthResult {
        public boolean isLoggedIn;
        public String userName;
        public AuthDto authDto;
    }

    /**
     * Given request, it returns an authresult
     * @param parsedRequest
     * @return
     */
    @NonNull
    public static AuthResult doFilter(ParsedRequest parsedRequest) {
        AuthDao authDao = AuthDao.getInstance();
        var result = new AuthResult();
        String hash = parsedRequest.getCookieValue("auth");

        if (hash == null) return result;

        Document filter = new Document("hash", hash);
        var authRes = authDao.query(filter);

        result.isLoggedIn = !authRes.isEmpty();
        
        if (!result.isLoggedIn) return result;

        result.authDto = authRes.getFirst();

        Long expiration = result.authDto.getExpireTime();

        if (expiration != null && expiration + 86400000 < Instant.now().toEpochMilli()) {
            result.isLoggedIn = false;
            authDao.delete(result.authDto);
            return result;
        }

        result.userName = result.authDto.getUserName();
        return result;
    }
}