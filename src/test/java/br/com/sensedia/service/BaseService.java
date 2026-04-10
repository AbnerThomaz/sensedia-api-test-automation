package br.com.sensedia.service;

import static br.com.sensedia.config.BaseConfig.BASE_URL;
import static io.restassured.RestAssured.given;

public class BaseService {

    protected io.restassured.specification.RequestSpecification request() {
        return given().baseUri(BASE_URL);
    }
}
