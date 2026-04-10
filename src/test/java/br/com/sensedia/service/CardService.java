package br.com.sensedia.service;

import io.restassured.response.Response;

import java.util.Map;

import static br.com.sensedia.config.BaseConfig.BASE_URL;
import static io.restassured.RestAssured.given;

public class CardService extends BaseService {

    public Response criarCard(Map<String, Object> params) {
        return request()
                .log().all()
                .queryParams(params)
                .when()
                .post("/cards");
    }

    public Response atualizarCard(String cardId, Map<String, Object> params) {
        return given()
                .queryParams(params)
                .when()
                .put("/cards/" + cardId);
    }

    public Response deletarCard(String cardId, Map<String, Object> params) {
        return given()
                .queryParams(params)
                .when()
                .delete("/cards/" + cardId);
    }

}


