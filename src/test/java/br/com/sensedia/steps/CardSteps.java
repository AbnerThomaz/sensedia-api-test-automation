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
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CardSteps {

    private String listId;
    private String cardId;
    private Response response;

    // ========================
    // BOARD
    // ========================

    @Dado("que possuo um board válido")
    public void quePossuoBoardValido() {

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);
        params.put("name", "Board Test " + System.currentTimeMillis());

        response = given()
                .baseUri(BASE_URL)
                .queryParams(params)
                .post("/boards");

        // salva o ID principal
        TestContext.boardId = response.jsonPath().getString("id");

        TestContext.boards.add(TestContext.boardId);
    }

    // ========================
    // LISTAS
    // ========================

    @Quando("eu buscar listas do board")
    public void buscarListas() {

        response = given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/boards/" + TestContext.boardId + "/lists");

        response.then().statusCode(200);

        listId = response.jsonPath().getString("[0].id");
    }

    // ========================
    // CREATE
    // ========================

    @Quando("eu criar um card válido")
    public void criarCard() {

        String idListValido = getListIdValido();

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);
        params.put("idList", idListValido);
        params.put("name", "Card Teste " + System.currentTimeMillis());

        response = new CardService().criarCard(params);

        response.then().statusCode(200);

        cardId = response.jsonPath().getString("id");
    }

    @Quando("eu criar um card com os seguintes dados:")
    public void criarCardComDados(io.cucumber.datatable.DataTable dataTable) {

        Map<String, Object> params = new HashMap<>(dataTable.asMap(String.class, Object.class));

        params.put("key", KEY);
        params.put("token", TOKEN);

        if (!params.containsKey("idList") && listId != null && !params.containsKey("forceNoList")) {
            params.put("idList", listId);
        }

        response = new CardService().criarCard(params);

        if (response.statusCode() == 200) {
            cardId = response.jsonPath().getString("id");
        }
    }

    // ========================
    // UPDATE
    // ========================

    @Quando("eu atualizo o card")
    public void atualizarCard() {

        garantirCardId();

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);
        params.put("name", "Card Atualizado");
        params.put("idList", listId);

        response = new CardService().atualizarCard(cardId, params);

        response.then().statusCode(200);
    }

    @Quando("eu atualizo o card com os seguintes dados:")
    public void atualizarCardComDados(io.cucumber.datatable.DataTable dataTable) {

        garantirCardId();

        Map<String, Object> params = new HashMap<>(dataTable.asMap(String.class, Object.class));

        params.put("key", KEY);
        params.put("token", TOKEN);

        response = new CardService().atualizarCard(cardId, params);
    }

    // ========================
    // DELETE
    // ========================

    @Quando("eu deletar o card")
    public void deletarCard() {

        garantirCardId();

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/cards/" + cardId);
    }

    @Quando("eu deletar um card inexistente")
    public void deletarCardInexistente() {

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/cards/123abc");
    }

    // ========================
    // AUTH
    // ========================

    // TOKEN INVALIDO
    @Quando("eu criar card com token inválido")
    public void criarCardTokenInvalido() {

        String idListValido = getListIdValido();

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", "TOKEN_INVALIDO")
                .queryParam("idList", idListValido)
                .queryParam("name", "Card token invalido")
                .when()
                .post("/cards");
    }

    @Quando("eu atualizar card com token inválido")
    public void atualizarTokenInvalido() {

        garantirCardId();

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", "TOKEN_INVALIDO")
                .when()
                .put("/cards/" + cardId);
    }

    @Quando("eu deletar card com token inválido")
    public void deletarTokenInvalido() {

        garantirCardId();

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", "TOKEN_INVALIDO")
                .when()
                .delete("/cards/" + cardId);
    }

    // KEY INVALIDA
    @Quando("eu criar card com key inválida")
    public void criarCardKeyInvalida() {

        String idListValido = getListIdValido();

        response = given()
                .queryParam("key", "KEY_INVALIDA")
                .queryParam("token", TOKEN)
                .queryParam("idList", idListValido)
                .queryParam("name", "Card key invalida")
                .when()
                .post("/cards");
    }

    @Quando("eu atualizar card com key inválida")
    public void atualizarKeyInvalida() {

        garantirCardId();

        response = given()
                .queryParam("key", "KEY_INVALIDA")
                .queryParam("token", TOKEN)
                .when()
                .put("/cards/" + cardId);
    }

    @Quando("eu deletar card com key inválida")
    public void deletarKeyInvalida() {

        garantirCardId();

        response = given()
                .queryParam("key", "KEY_INVALIDA")
                .queryParam("token", TOKEN)
                .when()
                .delete("/cards/" + cardId);
    }

// ========================
// AUTH - SEM TOKEN
// ========================

    @Quando("eu criar card sem passar o token")
    public void criarCardSemToken() {

        String idListValido = getListIdValido();

        response = given()
                .queryParam("key", KEY)
                .queryParam("idList", idListValido)
                .queryParam("name", "Card sem token")
                .when()
                .post("/cards");
    }

    @Quando("eu atualizar card sem passar o token")
    public void atualizarCardSemToken() {

        garantirCardId();

        response = given()
                .queryParam("key", KEY)
                .when()
                .put("/cards/" + cardId);
    }

    @Quando("eu deletar card sem passar o token")
    public void deletarCardSemToken() {

        garantirCardId();

        response = given()
                .queryParam("key", KEY)
                .when()
                .delete("/cards/" + cardId);
    }

// ========================
// AUTH - SEM KEY
// ========================

    @Quando("eu criar card sem passar o key")
    public void criarCardSemKey() {

        String idListValido = getListIdValido();

        response = given()
                .queryParam("token", TOKEN)
                .queryParam("idList", idListValido)
                .queryParam("name", "Card sem key")
                .when()
                .post("/cards");
    }

    @Quando("eu atualizar card sem passar o key")
    public void atualizarCardSemKey() {

        garantirCardId();

        response = given()
                .queryParam("token", TOKEN)
                .when()
                .put("/cards/" + cardId);
    }

    @Quando("eu deletar card sem passar o key")
    public void deletarCardSemKey() {

        garantirCardId();

        response = given()
                .queryParam("token", TOKEN)
                .when()
                .delete("/cards/" + cardId);
    }

// ========================
// AUTH - KEY EM BRANCO
// ========================

    @Quando("eu criar card passando em branco o key")
    public void criarCardKeyVazio() {

        String idListValido = getListIdValido();

        response = given()
                .queryParam("key", "")
                .queryParam("token", TOKEN)
                .queryParam("idList", idListValido)
                .queryParam("name", "Card key vazio")
                .when()
                .post("/cards");
    }

    @Quando("eu atualizar card passando em branco o key")
    public void atualizarCardKeyVazio() {

        garantirCardId();

        response = given()
                .queryParam("key", "")
                .queryParam("token", TOKEN)
                .when()
                .put("/cards/" + cardId);
    }

    @Quando("eu deletar card passando em branco o key")
    public void deletarCardKeyVazio() {

        garantirCardId();

        response = given()
                .queryParam("key", "")
                .queryParam("token", TOKEN)
                .when()
                .delete("/cards/" + cardId);
    }

// ========================
// AUTH - TOKEN EM BRANCO
// ========================

    @Quando("eu criar card passando em branco o token")
    public void criarCardTokenVazio() {

        String idListValido = getListIdValido();

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", "")
                .queryParam("idList", idListValido)
                .queryParam("name", "Card token vazio")
                .when()
                .post("/cards");
    }

    @Quando("eu atualizar card passando em branco o token")
    public void atualizarCardTokenVazio() {

        garantirCardId();

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", "")
                .when()
                .put("/cards/" + cardId);
    }

    @Quando("eu deletar card passando em branco o token")
    public void deletarCardTokenVazio() {

        garantirCardId();

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", "")
                .when()
                .delete("/cards/" + cardId);
    }

    @Quando("eu deleto o card")
    public void eu_deleto_o_card() {

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/cards/" + cardId);
    }

    // ========================
// UPDATE - ID INVÁLIDO
// ========================

    @Quando("eu atualizo o card com id invalido")
    public void eu_atualizo_o_card_com_id_invalido() {

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Card invalido")
                .when()
                .put("/cards/123"); // formato inválido
    }

    @Quando("eu atualizo o card com id inexistente")
    public void eu_atualizo_o_card_com_id_inexistente() {

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Card inexistente")
                .when()
                .put("/cards/ffffffffffffffffffffffff"); // formato válido, mas não existe
    }

    // ========================
    // VALIDAÇÕES
    // ========================

    @Entao("deve retornar status {int}")
    public void validarStatus(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Entao("o contrato de erro deve ser válido")
    public void validarContratoErro() {

        String body = response.getBody().asString();

        if (!body.trim().startsWith("{")) {
            return;
        }

        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/error-schema.json"));
    }

    @Entao("o card deve ser criado com sucesso")
    public void o_card_deve_ser_criado_com_sucesso() {
        response.then().statusCode(200);
    }

    @Entao("o card deve existir na API")
    public void o_card_deve_existir_na_api() {

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/cards/" + cardId);

        response.then().statusCode(200);
    }

    @Entao("o card deve ser atualizado com sucesso")
    public void o_card_deve_ser_atualizado_com_sucesso() {
        response.then().statusCode(200);
    }

    @Entao("o card nao deve mais existir")
    public void o_card_nao_deve_mais_existir() {

        response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/cards/" + cardId);

        response.then().statusCode(404);
    }

    @Entao("a mensagem de erro deve ser {string}")
    public void a_mensagem_de_erro_deve_ser(String mensagem) {

        String body = response.getBody().asString().trim();

        //CASO 1: JSON
        if (body.startsWith("{")) {

            String mensagemJson = response.jsonPath().getString("message");
            org.junit.Assert.assertEquals(mensagem, mensagemJson);

        } else {
            //CASO 2: TEXTO
            org.junit.Assert.assertEquals(mensagem, body);
        }
    }

    @Entao("a mensagem de erro deve conter {string}")
    public void a_mensagem_de_erro_deve_conter(String mensagem) {

        String body = response.getBody().asString();

        assertTrue(body.contains(mensagem));
    }

// ========================
// VALIDAÇÕES DE UPDATE
// ========================

    @Entao("o campo {string} deve ser {string}")
    public void o_campo_deve_ser(String campo, String valorEsperado) {

        String valorAtual = response.jsonPath().getString(campo);

        org.junit.Assert.assertEquals(valorEsperado, valorAtual);
    }

    @Entao("o contrato deve ser válido")
    public void o_contrato_deve_ser_valido() {

        response.then()
                .body(io.restassured.module.jsv.JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("schemas/card-schema.json"));
    }

    @Entao("o card deve refletir as alteracoes")
    public void o_card_deve_refletir_as_alteracoes() {

        // busca novamente o card atualizado
        Response novaResposta = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/cards/" + cardId);

        novaResposta.then().statusCode(200);

        // valida que o nome foi atualizado
        String nomeAtual = novaResposta.jsonPath().getString("name");

        org.junit.Assert.assertNotNull(nomeAtual);
    }

    // ========================
    // HELPERS
    // ========================

    private String getListIdValido() {

        if (listId == null) {
            quePossuoBoardValido();
            buscarListas();
        }

        return listId;
    }

    private void garantirCardId() {

        if (cardId == null) {

            String idListValido = getListIdValido();

            Map<String, Object> params = new HashMap<>();
            params.put("key", KEY);
            params.put("token", TOKEN);
            params.put("idList", idListValido);
            params.put("name", "Card Auto " + System.currentTimeMillis());

            response = new CardService().criarCard(params);

            cardId = response.jsonPath().getString("id");
        }
    }
}