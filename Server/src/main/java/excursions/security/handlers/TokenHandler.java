package excursions.security.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import excursions.models.User;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

public class TokenHandler {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SEPARATOR = ".";
    private static final String SEPARATOR_SPLITTER = "\\.";

    private final Mac hmac;

    public TokenHandler(byte[] secretKey){
        try{
            hmac = Mac.getInstance(HMAC_ALGORITHM);
            hmac.init(new SecretKeySpec(secretKey, HMAC_ALGORITHM));
        }catch(NoSuchAlgorithmException | InvalidKeyException e){
            throw new IllegalStateException("Failed to initialize HMAC: " + e.getMessage(), e);
        }
    }

    public User parseUserFromToken(String token){
        final String[] parts = token.split(SEPARATOR_SPLITTER);

        //Check if the token is in a valid format
        if(parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0){
            try{
                final byte[] userBytes = fromBase64(parts[0]);
                final byte[] hash = fromBase64(parts[1]);

                boolean isValidHash = Arrays.equals(createHmac(userBytes), hash);

                if(isValidHash){
                    final User user = fromJSON(userBytes);
                    if(new Date().getTime() < user.getExpires()){
                        return user;
                    }
                }
            }catch(IllegalArgumentException e){
                //Todo: Log tampering attempt
            }
        }

        return null;
    }

    public String createTokenForUser(User user){
        byte[] userBytes = toJSON(user);
        byte[] hash = createHmac(userBytes);
        return toBase64(userBytes) + SEPARATOR + toBase64(hash);
    }

    private User fromJSON(final byte[] userBytes){
        try{
            return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), User.class);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private byte[] toJSON(User user){
        try{
            return new ObjectMapper().writeValueAsBytes(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toBase64(byte[] content){
        return DatatypeConverter.printBase64Binary(content);
    }

    private byte[] fromBase64(String content) {
        return DatatypeConverter.parseBase64Binary(content);
    }

    private synchronized byte[] createHmac(byte[] content){
        return hmac.doFinal(content);
    }
}
