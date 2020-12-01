package jrh.game.service.account;

import io.javalin.core.security.Role;

public enum AccountRole implements Role {
    ANYONE, LOGGED_IN, NOT_LOGGED_IN
}
