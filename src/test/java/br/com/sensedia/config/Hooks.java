package br.com.sensedia.config;

import br.com.sensedia.service.BoardService;
import br.com.sensedia.utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

import static br.com.sensedia.config.BaseConfig.*;

public class Hooks {

    private BoardService service = new BoardService();

    @Before
    public void setup() {
        System.out.println("🔥 CONFIGURANDO BASE URI");
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void limpar() {

        System.out.println("🔥 EXECUTANDO HOOK DE LIMPEZA");

        if (TestContext.boards == null || TestContext.boards.isEmpty()) {
            System.out.println("Nenhum board para deletar.");
            return;
        }

        for (String boardId : TestContext.boards) {

            try {
                System.out.println("🧹 Deletando board: " + boardId);

                service.deletarBoard(boardId, KEY, TOKEN);

                System.out.println("✅ Board deletado com sucesso: " + boardId);

            } catch (Exception e) {

                // 🔥 NÃO quebra o teste se já tiver sido deletado ou não existir
                System.out.println("⚠️ Erro ao deletar board: " + boardId);
                System.out.println("Motivo: " + e.getMessage());
            }
        }

        // 🔥 MUITO IMPORTANTE: limpar lista para próximo cenário
        TestContext.boards.clear();
    }
}