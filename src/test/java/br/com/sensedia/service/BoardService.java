package br.com.sensedia.service;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BoardService {

    public Response criarBoard(Map<String, Object> params) {
        return given()
                .queryParams(params)
                .when()
                .post("/boards/");
    }

    public Response deletarBoard(String boardId, String key, String token) {

        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .delete("/boards/" + boardId);
    }
}