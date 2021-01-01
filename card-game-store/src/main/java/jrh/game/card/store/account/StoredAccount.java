package jrh.game.card.store.account;

import java.util.List;
import java.util.UUID;

public class StoredAccount {

    private UUID id;
    private String name;
    private String email;
    private String bcrypt;
    private long registered;
    private List<String> roles;

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

    public long getRegistered() {
        return registered;
    }

    public void setRegistered(long registered) {
        this.registered = registered;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
