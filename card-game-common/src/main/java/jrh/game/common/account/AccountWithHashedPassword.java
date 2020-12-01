package jrh.game.common.account;

public class AccountWithHashedPassword {

    private final Account account;
    private final String hashedPassword;

    public AccountWithHashedPassword(Account account, String hashedPassword) {
        this.account = account;
        this.hashedPassword = hashedPassword;
    }

    public Account getAccount() {
        return account;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}
