package jrh.game.service.account.response;

public class FailedToRegisterResponse {

    private final String reason;
    private final String details;

    public static FailedToRegisterResponse usernameAlreadyExists() {
        return new FailedToRegisterResponse("Username already taken");
    }

    public static FailedToRegisterResponse emailAddressAlreadyExists() {
        return new FailedToRegisterResponse("Email already taken");
    }

    public static FailedToRegisterResponse invalidEmailAddress() {
        return new FailedToRegisterResponse("Invalid email address");
    }

    public static FailedToRegisterResponse invalidPassword() {
        return new FailedToRegisterResponse("Invalid password",
                "Must be at least 8 characters, containing only letters, numbers, and these special characters: @#$%^&+=!*");
    }

    public static FailedToRegisterResponse invalidUsername() {
        return new FailedToRegisterResponse("Invalid username",
                "Must be at least 3 characters, containing only letters, numbers, and these special characters: .-_");

    }

    private FailedToRegisterResponse(String reason) {
        this(reason, null);
    }

    private FailedToRegisterResponse(String reason, String details) {
        this.reason = reason;
        this.details = details;
    }

    public String getReason() {
        return reason;
    }

    public String getDetails() {
        return details;
    }
}
