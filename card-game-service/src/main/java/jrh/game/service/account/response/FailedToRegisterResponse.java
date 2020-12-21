package jrh.game.service.account.response;

public class FailedToRegisterResponse {

    private final String reason;
    private final String details;

    public static FailedToRegisterResponse usernameAlreadyExists() {
        return new FailedToRegisterResponse("username", "Username already taken");
    }

    public static FailedToRegisterResponse emailAddressAlreadyExists() {
        return new FailedToRegisterResponse("email", "Email already taken");
    }

    public static FailedToRegisterResponse invalidEmailAddress() {
        return new FailedToRegisterResponse("email", "Invalid email address");
    }

    public static FailedToRegisterResponse invalidPassword() {
        return new FailedToRegisterResponse("password",
                "Must be 8+ characters");
    }

    public static FailedToRegisterResponse invalidUsername() {
        return new FailedToRegisterResponse("username", "Must be 3+ characters of only: letters, numbers, or .-_");

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
