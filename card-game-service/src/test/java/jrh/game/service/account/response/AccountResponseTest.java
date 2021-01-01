package jrh.game.service.account.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.common.account.Role;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class AccountResponseTest {

    private final ObjectMapper objectMapper = ObjectMapperFactory.create();

    @Test
    public void serialisesAccountResponseToJson() throws JsonProcessingException, JSONException {
        Account account = new Account(AccountId.randomAccountId(), randomAlphanumeric(10), randomAlphanumeric(10),
                Instant.now(), singleton(Role.ADMIN));
        AccountResponse response = new AccountResponse(account);

        String json = objectMapper.writeValueAsString(response);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("accountId", account.getId().toString());
        expectedResponse.put("name", account.getName());
        expectedResponse.put("roles", singleton(Role.ADMIN.toString()));

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expectedResponse), json, JSONCompareMode.STRICT);
    }
}
