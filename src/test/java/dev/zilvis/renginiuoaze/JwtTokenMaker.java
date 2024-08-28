package dev.zilvis.renginiuoaze;

import jakarta.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtTokenMaker {
    @Test
    public void generateSecertKey(){
        SecretKey key = Jwts.SIG.HS256.key().build();
        String encodedKey = DatatypeConverter.printHexBinary(key.getEncoded());
        System.out.printf("\nKey = [%s]\n",encodedKey);
    }
}
