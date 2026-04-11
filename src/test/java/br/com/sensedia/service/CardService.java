package br.com.sensedia.service;

import io.restassured.response.Response;

import java.util.Map;

public class CardService extends BaseService {

    public Response criarCard(Map<String, Object> params) {
        return request()
                .log().all()
                .queryParams(params)
                .when()
                .post("/cards");
    }

    public Response atualizarCard(String cardId, Map<String, Object> params) {
        return request()
                .log().all()
                .queryParams(params)
                .when()
                .put("/cards/" + cardId);
    }

    public Response deletarCard(String cardId, Map<String, Object> params) {
        return request()
                .log().all()
                .queryParams(params)
                .when()
                .delete("/cards/" + cardId);
    }

    public Response buscarCard(String cardId, Map<String, Object> params) {
        return request()
                .log().all()
                .queryParams(params)
                .when()
                .get("/cards/" + cardId);
    }
}





