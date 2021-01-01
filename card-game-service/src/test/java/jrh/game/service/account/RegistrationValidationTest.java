package jrh.game.service.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationValidationTest {

    @Test
    public void myEmailIsValid() {
        assertTrue(RegistrationValidation.isValidEmail("richard@revelation218.com"));
    }

    @Test
    public void reasonableUsernameIsValid() {
        assertTrue(RegistrationValidation.isValidUsername("jrh206"));
    }

    @Test
    public void randomPasswordIsValid() {
        assertTrue(RegistrationValidation.isValidPassword("%adsfhAE!DAY$%@^"));
        assertTrue(RegistrationValidation.isValidPassword("% !DAY$adsf345@123098fsdv9p8+_$Q@%N\" W%@^"));
    }

    @Test
    public void invalidEmailsAreNotValid() {
        assertFalse(RegistrationValidation.isValidEmail(null));
        assertFalse(RegistrationValidation.isValidEmail(""));
        assertFalse(RegistrationValidation.isValidEmail("sfd90 .xom"));
        assertFalse(RegistrationValidation.isValidEmail("sfd90 @s.xom"));
        assertFalse(RegistrationValidation.isValidEmail("...@."));
    }

    @Test
    public void invalidUsernamesAreIotValid() {
        assertFalse(RegistrationValidation.isValidUsername(null));
        assertFalse(RegistrationValidation.isValidUsername(""));
        assertFalse(RegistrationValidation.isValidUsername("ab"));
        assertFalse(RegistrationValidation.isValidUsername("£££"));
        assertFalse(RegistrationValidation.isValidUsername("!!1"));
        assertFalse(RegistrationValidation.isValidUsername("joe blogs"));
        assertFalse(RegistrationValidation.isValidUsername("verylongusernameeeeeeeeeeeeeeeee"));
    }

    @Test
    public void invalidPasswordAreNotValid() {
        assertFalse(RegistrationValidation.isValidPassword(null));
        assertFalse(RegistrationValidation.isValidPassword(""));
        assertFalse(RegistrationValidation.isValidPassword("abcdefg"));
        assertFalse(RegistrationValidation.isValidPassword("NKDSA"));
    }
}
