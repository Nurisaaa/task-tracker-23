package peaksoft.school.tasktracker;

import org.junit.jupiter.api.Test;
import peaksoft.school.tasktracker.security.JwtService;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private static final String SECRET = Base64.getEncoder().encodeToString(new byte[64]);

    @Test
    void generatedTokenIsValidAndContainsEmail() {
        JwtService service = new JwtService(SECRET, 60_000);
        String token = service.generateToken("user@mail.kg", "DEVELOPER");

        assertTrue(service.isValid(token));
        assertEquals("user@mail.kg", service.extractEmail(token));
    }

    @Test
    void expiredTokenIsInvalid() {
        JwtService service = new JwtService(SECRET, -1_000);
        String token = service.generateToken("user@mail.kg", "DEVELOPER");

        assertFalse(service.isValid(token));
    }

    @Test
    void garbageTokenIsInvalid() {
        JwtService service = new JwtService(SECRET, 60_000);
        assertFalse(service.isValid("not.a.jwt"));
    }
}
