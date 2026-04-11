# language: pt

@All
Funcionalidade: Atualizacao de Card

# =========================
# POSITIVOS
# =========================

  Cenario: Atualizar nome do card com sucesso

    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizo o card com os seguintes dados:
      | name | Card atualizado |
    Entao deve retornar status 200
    E o campo "name" deve ser "Card atualizado"
    E o contrato deve ser válido
    E o card deve refletir as alteracoes

# =========================

  Cenario: Atualizar descricao do card

    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizo o card com os seguintes dados:
      | desc | descricao atualizada |
    Entao deve retornar status 200
    E o campo "desc" deve ser "descricao atualizada"
    E o card deve refletir as alteracoes

# =========================

  Cenario: Atualizar multiplos campos

    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizo o card com os seguintes dados:
      | name | Card completo |
      | desc | descricao teste |
      | closed | true |
    Entao deve retornar status 200
    E o contrato deve ser válido
    E o card deve refletir as alteracoes

# =========================
# NEGATIVOS
# =========================

  Cenario: Atualizar card com id invalido

    Dado que possuo um board válido
    Quando eu buscar listas do board
    Quando eu atualizo o card com id invalido
    Entao deve retornar status 400
    E a mensagem de erro deve ser "invalid id"
    E o contrato de erro deve ser válido

# =========================

  Cenario: Atualizar card inexistente

    Dado que possuo um board válido
    Quando eu buscar listas do board
    Quando eu atualizo o card com id inexistente
    Entao deve retornar status 404
    E a mensagem de erro deve conter "not found"