package com.clevertec.testapp.supermarket.json;

import com.clevertec.testapp.supermarket.entity.Cashier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class JSONParserTest {
    private final Cashier cashier = new Cashier(11L, "Sidorin", 'm', 2000.0, "sidorin@gmail.com");
    private ObjectMapper objectMapper = new ObjectMapper();
    private JSONParser jsonParser = new JSONParser();

    @Test
    void checkJSONParserWithJacksonParser() throws JsonProcessingException, IllegalAccessException {
        String expected = objectMapper.writeValueAsString(cashier);
        String actual = jsonParser.objectWrite(cashier);
        assertThat(actual).isEqualTo(expected);
    }

}