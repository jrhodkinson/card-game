package jrh.game.service.account;

import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.service.Cookies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

import static jrh.game.service.Attributes.ACCOUNT_ID;
import static jrh.game.service.account.AccountRole.ADMIN;
import static jrh.game.service.account.AccountRole.ANYONE;

public class SessionAccessManager implements AccessManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionAccessManager.class);

    private final Cookies cookies;
    private final Sessions sessions;
    private final Accounts accounts;

    public SessionAccessManager(Cookies cookies, Sessions sessions, Accounts accounts) {
        this.cookies = cookies;
        this.sessions = sessions;
        this.accounts = accounts;
    }

    @Override
    public void manage(Handler handler, Context context, Set<Role> permittedRoles) throws Exception {
        boolean isLoggedIn = false;

        // This needs to happen even if permittedRoles.contains(ANYONE) so that context.attribute is set!
        Optional<AccountId> optionalAccountId = cookies.accountId(context);
        Optional<Token> optionalToken = cookies.token(context);
        if (optionalAccountId.isPresent() && optionalToken.isPresent()) {
            AccountId accountId = optionalAccountId.get();
            Token token = optionalToken.get();
            if (sessions.isValid(accountId, token)) {
                context.attribute(ACCOUNT_ID, accountId);
                isLoggedIn = true;
            }
        }

        if (permittedRoles.contains(ANYONE)) {
            handler.handle(context);
            return;
        }

        if (!isLoggedIn) {
            throw new UnauthorizedResponse();
        }

        if (permittedRoles.contains(ADMIN)) {
           Account account = accounts.getAccount(optionalAccountId.orElseThrow());
           if (account.isAdmin()) {
               handler.handle(context);
           } else {
               throw new UnauthorizedResponse();
           }
        } else {
            handler.handle(context);
        }
    }
}
