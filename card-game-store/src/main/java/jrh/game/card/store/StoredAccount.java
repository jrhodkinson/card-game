package jrh.game.card.store;

import java.util.UUID;

public class StoredAccount {

    private UUID id;
    private String name;
    private String email;
    private String bcrypt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBcrypt() {
        return bcrypt;
    }

    public void setBcrypt(String bcrypt) {
        this.bcrypt = bcrypt;
    }
}
