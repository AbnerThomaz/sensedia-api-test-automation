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
      | name        | Card completo |
      | desc        | descricao teste |
      | pos         | top |
      | due         | 2026-12-31 |
      | start       | 2026-12-01 |
      | dueComplete | true |
    Entao o card deve ser criado com sucesso


  Cenario: Criar card sem idList

    Quando eu criar um card com os seguintes dados:
      | name | Card invalido |
    Entao deve retornar status 400