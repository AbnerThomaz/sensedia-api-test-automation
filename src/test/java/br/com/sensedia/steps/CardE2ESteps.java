package br.com.sensedia.steps;

import br.com.sensedia.service.BoardService;
import br.com.sensedia.service.CardService;
import br.com.sensedia.utils.TestContext;

import io.cucumber.java.pt.*;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static br.com.sensedia.config.BaseConfig.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CardE2ESteps {

    private String cardId;
    private Response response;

    private String listaAFazer;
    private String listaEmAndamento;
    private String listaConcluido;

    // ========================
    // BOARD FIXO
    // ========================
    @Dado("que utilizo um board fixo E2E")
    public void usarBoardFixoE2E() {

        TestContext.boardId = "69d96986882c560cbb16159d";

        System.out.println("Board utilizado: " + TestContext.boardId);
    }

    // ========================
    // LISTAS
    // ========================
    @Quando("eu busco as listas do board E2E")
    public void buscarListasE2E() {

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);

        response = new BoardService()
                .buscarListasDoBoard(TestContext.boardId, params);

        response.then().statusCode(200);

        for (int i = 0; i < response.jsonPath().getList("$").size(); i++) {

            String nome = response.jsonPath().getString("[" + i + "].name");
            String id = response.jsonPath().getString("[" + i + "].id");

            if (nome.equalsIgnoreCase("A fazer")) {
                listaAFazer = id;
            } else if (nome.equalsIgnoreCase("Em andamento")) {
                listaEmAndamento = id;
            } else if (nome.equalsIgnoreCase("Concluído")) {
                listaConcluido = id;
            }
        }
    }

    // ========================
    // CREATE
    // ========================
    @Quando("eu crio um card E2E")
    public void criarCardE2E() {

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);
        params.put("idList", listaAFazer);
        params.put("name", "Card E2E " + System.currentTimeMillis());

        response = new CardService().criarCard(params);

        response.then().statusCode(200);

        cardId = response.jsonPath().getString("id");

        System.out.println("Card criado: " + cardId);
    }

    @Entao("o card E2E deve ser criado com sucesso")
    public void validarCriacaoE2E() {
        response.then().statusCode(200);
    }

    // ========================
    // UPDATE (MOVIMENTAÇÃO)
    // ========================
    @Quando("eu movo o card entre listas E2E")
    public void moverCardE2E() {

        // EM ANDAMENTO
        Map<String, Object> params1 = new HashMap<>();
        params1.put("key", KEY);
        params1.put("token", TOKEN);
        params1.put("idList", listaEmAndamento);

        response = new CardService().atualizarCard(cardId, params1);

        response.then().statusCode(200);
        response.then().body("idList", equalTo(listaEmAndamento));

        System.out.println("Movido para EM ANDAMENTO");

        // CONCLUÍDO
        Map<String, Object> params2 = new HashMap<>();
        params2.put("key", KEY);
        params2.put("token", TOKEN);
        params2.put("idList", listaConcluido);

        response = new CardService().atualizarCard(cardId, params2);

        response.then().statusCode(200);
        response.then().body("idList", equalTo(listaConcluido));

        System.out.println("Movido para CONCLUÍDO");
    }

    @Então("o card deve ser movido entre as listas")
    public void validarMovimentacaoE2E() {

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);

        Response novaResposta = new CardService()
                .buscarCard(cardId, params);

        novaResposta.then().statusCode(200);

        String idListAtual = novaResposta.jsonPath().getString("idList");

        org.junit.Assert.assertEquals(listaConcluido, idListAtual);

    }

    // ========================
    // DELETE
    // ========================
    @Quando("eu removo o card E2E")
    public void deletarCardE2E() {

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);

        response = new CardService().deletarCard(cardId, params);

        System.out.println("Card deletado: " + cardId);
    }

    @Entao("o card E2E nao deve mais existir")
    public void validarDeleteE2E() {

        Map<String, Object> params = new HashMap<>();
        params.put("key", KEY);
        params.put("token", TOKEN);

        response = new CardService()
                .buscarCard(cardId, params);

        response.then().statusCode(404);

        System.out.println("Card confirmado como deletado");
    }
}