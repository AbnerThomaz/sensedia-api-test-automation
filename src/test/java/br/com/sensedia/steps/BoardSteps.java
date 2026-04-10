package br.com.sensedia.steps;

import br.com.sensedia.service.BoardService;
import br.com.sensedia.utils.TestContext;
import io.cucumber.java.pt.*;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static br.com.sensedia.config.BaseConfig.*;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;

public class BoardSteps {

    private Response response;
    private BoardService service = new BoardService();
    private Map<String, Object> params;

    // ========================
    // GIVEN
    // ========================

    @Dado("que possuo uma key e token válidos")
    public void credenciaisValidas() {
        baseURI = BASE_URL;

        params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);
    }

    @Dado("que possuo uma key inválida")
    public void keyInvalida() {
        credenciaisValidas();
        params.put("key", "key_invalida");
    }

    @Dado("que possuo um token inválido")
    public void tokenInvalido() {
        credenciaisValidas();
        params.put("token", "token_invalido");
    }

    @Dado("que não informo token")
    public void semToken() {
        credenciaisValidas();
        params.remove("token");
    }

    // ========================
    // WHEN
    // ========================

    @Quando("eu criar um board com nome {string}")
    public void criarBoard(String nome) {
        params.put("name", nome + System.currentTimeMillis()); // evita conflito
        response = service.criarBoard(params);
    }

    @Quando("eu criar um board sem nome")
    public void criarSemNome() {
        response = service.criarBoard(params);
    }

    @Quando("eu tentar criar um board")
    public void tentarCriarBoard() {
        params.put("name", "Board Teste");
        response = service.criarBoard(params);
    }

    @Quando("eu criar um board com nome de {int} caracteres")
    public void nomeGrande(int tamanho) {
        String nome = "A".repeat(tamanho);
        params.put("name", nome);
        response = service.criarBoard(params);
    }

    @Quando("eu criar um board com permissionLevel {string}")
    public void permissionLevel(String valor) {
        params.put("name", "Board Teste" + System.currentTimeMillis());
        params.put("prefs_permissionLevel", valor);
        response = service.criarBoard(params);
    }

    @Quando("eu criar um board com defaultLabels {string}")
    public void defaultLabels(String valor) {
        params.put("name", "Board Teste" + System.currentTimeMillis());
        params.put("defaultLabels", valor);
        response = service.criarBoard(params);
    }

    // ========================
    // THEN
    // ========================

    @Entao("o board deve ser criado com sucesso")
    public void sucesso() {

        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", notNullValue());

        String id = response.jsonPath().getString("id");

        // SALVA TODOS
        TestContext.boards.add(id);
    }

    @Entao("deve retornar erro {int}")
    public void erro(int status) {
        response.then().statusCode(status);
    }

    @Entao("deve retornar erro {int} com mensagem {string}")
    public void erroComMensagem(int status, String mensagem) {

        response.then().statusCode(status);

        String body = response.getBody().asString();

        if (body.startsWith("{")) {
            response.then().body("message", containsString(mensagem));
        } else {
            response.then().body(equalTo(mensagem));
        }
    }

    @Entao("deve retornar erro de tamanho de requisição")
    public void erro414() {
        response.then().statusCode(414);
    }
}