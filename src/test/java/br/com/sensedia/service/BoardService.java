package br.com.sensedia.service;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;

public class BoardService extends BaseService {

    public Response criarBoard(Map<String, Object> params) {
        return request()
                .queryParams(params)
                .when()
                .post("/boards/");
    }

    public Response buscarListasDoBoard(String boardId, Map<String, Object> params) {

        return request()
                .log().all()
                .queryParams(params)
                .when()
                .get("/boards/" + boardId + "/lists");
    }

    public Response deletarBoard(String boardId, String key, String token) {

        return request()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .delete("/boards/" + boardId);
    }
}