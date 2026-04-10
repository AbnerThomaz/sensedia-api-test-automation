package br.com.sensedia.steps;

import br.com.sensedia.service.CardService;
import br.com.sensedia.utils.TestContext;

import io.cucumber.java.pt.*;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.*;
import static br.com.sensedia.config.BaseConfig.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CardCreateSteps {

    private String listId;
    private String cardId;
    private Response response;

    // ========================
    // BOARD (REUTILIZANDO)
    // ========================

    @Dado("que possuo um board válido")
    public void quePossuoBoardValido() {

        if (TestContext.boardId == null) {

            System.out.println("🔥 Criando novo board...");

            Map<String, Object> params = new HashMap<>();
            params.put("key", KEY);
            params.put("token", TOKEN);
            params.put("name", "Board Global Test " + System.currentTimeMillis());

            response = given()
                    .baseUri(BASE_URL)
                    .log().all()
                    .queryParams(params)
                    .when()
                    .post("/boards");

            response.then().statusCode(200);

            TestContext.boardId = response.jsonPath().getString("id");
        }

        System.out.println("BOARD EM USO >>> " + TestContext.boardId);
    }

    // ========================
    // LISTAS
    // ========================

    @Quando("eu buscar listas do board")
    public void buscarListas() {

        baseURI = BASE_URL;

        response = given()
                .log().all()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/boards/" + TestContext.boardId + "/lists");

        response.then().statusCode(200);

        listId = response.jsonPath().getString("[0].id");

        System.out.println("LIST ID >>> " + listId);
    }

    // ========================
    // CARD SIMPLES
    // ========================

    @Quando("eu criar um card válido")
    public void criarCard() {

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);
        params.put("idList", listId);
        params.put("name", "Card Teste " + System.currentTimeMillis());

        response = new CardService().criarCard(params);

        response.then().statusCode(200);

        cardId = response.jsonPath().getString("id");
    }

    // ========================
    // CARD DINÂMICO
    // ========================

    @Quando("eu criar um card com os seguintes dados:")
    public void criarCardComDados(io.cucumber.datatable.DataTable dataTable) {

        baseURI = BASE_URL;

        Map<String, Object> params = new HashMap<>(dataTable.asMap(String.class, Object.class));

        params.put("key", KEY);
        params.put("token", TOKEN);

        if (!params.containsKey("idList") && listId != null) {
            params.put("idList", listId);
        }

        System.out.println("PARAMS >>> " + params);

        response = new CardService().criarCard(params);

        response.then().log().all();

        if (response.statusCode() == 200) {
            cardId = response.jsonPath().getString("id");
        }
    }

    @Quando("eu atualizo o card")
    public void atualizarCard() {

        System.out.println("\n===== ATUALIZANDO CARD =====");

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);
        params.put("name", "Card Atualizado");
        params.put("idList", listId);

        response = new CardService().atualizarCard(cardId, params);

        response.then().statusCode(200);
    }

    // ========================
    // VALIDAÇÕES
    // ========================

    @Entao("o card deve ser criado com sucesso")
    public void validarCardCriado() {

        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", notNullValue())
                .body("idList", equalTo(listId))
                .body("closed", equalTo(false));
    }

    @Entao("o card deve existir na API")
    public void validarCardExiste() {

        given()
                .log().all()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/cards/" + cardId)
                .then()
                .statusCode(200)
                .body("id", equalTo(cardId));
    }

    @Entao("o card deve ser atualizado com sucesso")
    public void validarUpdate() {

        response.then()
                .statusCode(200)
                .body("name", equalTo("Card Atualizado"));
    }

    @Entao("deve retornar status {int}")
    public void validarStatus(int status) {
        response.then().statusCode(status);
    }

    @Entao("o contrato deve ser válido")
    public void validarContrato() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/card-schema.json"));
    }

    // ========================
    // DELETE
    // ========================

    @Quando("eu deleto o card")
    public void deletarCard() {

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);

        response = new CardService().deletarCard(cardId, params);

        response.then().statusCode(200);
    }

    @Entao("o card nao deve mais existir")
    public void validarDelete() {

        given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/cards/" + cardId)
                .then()
                .statusCode(404);
    }

    @Entao("a mensagem de erro deve ser {string}")
    public void validarMensagemErro(String mensagemEsperada) {

        String responseBody = response.getBody().asString();

        if (responseBody.startsWith("{")) {
            response.then().body("message", equalTo(mensagemEsperada));
        } else {
            if (!responseBody.contains(mensagemEsperada)) {
                throw new AssertionError("Mensagem não encontrada: " + responseBody);
            }
        }
    }

    @Entao("a mensagem de erro deve conter {string}")
    public void validarMensagemErroContem(String mensagemEsperada) {

        String responseBody = response.getBody().asString();

        if (!responseBody.toLowerCase().contains(mensagemEsperada.toLowerCase())) {
            throw new AssertionError("Mensagem não contém esperado: " + responseBody);
        }
    }

    @Entao("o card nao deve possuir membros")
    public void validarSemMembros() {
        response.then().body("idMembers.size()", equalTo(0));
    }

    @Entao("o contrato de erro deve ser válido")
    public void validarContratoErro() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/error-schema.json"));
    }
}

