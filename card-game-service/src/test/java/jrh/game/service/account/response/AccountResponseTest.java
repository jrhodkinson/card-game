package jrh.game.service.account.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;

import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountResponseTest {

    @Test
    public void serialisesAccountResponseToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = ObjectMapperFactory.create();
        Account account = new Account(AccountId.randomAccountId(), randomAlphanumeric(10), randomAlphanumeric(10),
                Instant.now(), emptySet());
        AccountResponse response = new AccountResponse(account);
        Map<String, String> json = objectMapper.readValue(objectMapper.writeValueAsString(response),
                new TypeReference<>() {
                });
        Map<String, String> expectedJson = Map.of("accountId", account.getId().toString(), "name", account.getName());
        assertThat(json, equalTo(expectedJson));
    }
}
