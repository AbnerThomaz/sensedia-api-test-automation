# language: pt

Funcionalidade: CRUD de Card no Trello

  Cenario: Fluxo completo de card

    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Entao o card deve ser criado com sucesso
    E o card deve existir na API
    Quando eu atualizo o card
    Entao o card deve ser atualizado com sucesso
    Quando eu deleto o card
    Entao o card nao deve mais existir


  Cenario: Criar card com dados mínimos

    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name | Card simples |
    Entao o card deve ser criado com sucesso


  Cenario: Criar card com todos os campos

    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name        | Card completo   |
      | desc        | descricao teste |
      | pos         | top             |
      | due         | 2026-12-31      |
      | start       | 2026-12-01      |
      | dueComplete | true            |
    Entao o card deve ser criado com sucesso

  Cenario: Criar card sem idList
    Dado que possuo um board válido
    Quando eu buscar listas do board
    Quando eu criar um card com os seguintes dados:
      | name        | Card invalido |
      | forceNoList | true          |
    Entao deve retornar status 400


  # =========================
  # TESTES NEGATIVOS (CREATE)
  # =========================

  Cenario: Criar card com idLabels inválido
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name     | Card teste  |
      | idLabels | invalido123 |
    Entao deve retornar status 400
    E a mensagem de erro deve ser "Invalid objectId"
    E o contrato de erro deve ser válido


  Cenario: Criar card com idMembers fora do padrão
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name      | Card teste |
      | idMembers | 123abc     |
    Entao deve retornar status 400
    E a mensagem de erro deve ser "Invalid objectId"
    E o contrato de erro deve ser válido


  Cenario: Criar card com idMembers válido porém inexistente
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name      | Card teste               |
      | idMembers | 9fd999eb1db99c99cac99ce9 |
    Entao o card deve ser criado com sucesso

  Cenario: Criar card com idList inválido
    Dado que possuo um board válido
    Quando eu buscar listas do board
    Quando eu criar um card com os seguintes dados:
      | name   | Card teste |
      | idList | 123        |
    Entao deve retornar status 400
    E a mensagem de erro deve conter "invalid value for idList"

  Cenario: Criar card com idCardSource inexistente
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name         | Card teste               |
      | idCardSource | a3f9c8e12b4d6f7a9c0e1d2b |
    Entao deve retornar status 404
    E a mensagem de erro deve conter "not found"



  Cenario: Criar card com idCardSource inválido
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name         | Card teste |
      | idCardSource | 123qd      |
    Entao deve retornar status 400
    E a mensagem de erro deve ser "Invalid objectId"
    E o contrato de erro deve ser válido

  Cenario: Criar card com mimeType inválido
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name     | Card teste                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      | mimeType | xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx |
    Entao deve retornar status 400
    E a mensagem de erro deve conter "invalid value for mimeType"

  Cenario: Criar card com idList vazio
    Dado que possuo um board válido
    Quando eu buscar listas do board
    Quando eu criar um card com os seguintes dados:
      | name   | Card teste |
      | idList |            |
    Entao deve retornar status 400
    E a mensagem de erro deve conter "invalid value for idList"

  Cenario: Criar card com coordinates inválido
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card com os seguintes dados:
      | name        | Card teste |
      | coordinates | teste      |
    Entao deve retornar status 400
    E a mensagem de erro deve ser "invalid value for coordinates"


