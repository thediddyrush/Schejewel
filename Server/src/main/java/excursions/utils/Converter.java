package excursions.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import excursions.models.User;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Converter {

    public static byte[] fromBase64(String content) {
        return DatatypeConverter.parseBase64Binary(content);
    }

    public static User fromJSON(final byte[] userBytes){
        try{
            return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), User.class);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
