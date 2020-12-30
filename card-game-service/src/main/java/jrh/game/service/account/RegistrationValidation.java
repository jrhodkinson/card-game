package jrh.game.service.account;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

public class RegistrationValidation {

    private static final Pattern USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9._\\-]{3,18}$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^\\p{Print}{8,}$");

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        return USERNAME_REGEX.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return PASSWORD_REGEX.matcher(password).matches();
    }
}
