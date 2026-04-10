# language: pt

Funcionalidade: Criação de Boards no Trello

  # Cenário de sucesso principal
  Cenário: Criar board com sucesso
    Dado que possuo uma key e token válidos
    Quando eu criar um board com nome "Board Teste"
    Então o board deve ser criado com sucesso

  # Cenários manipulando o parâmetro 'key'
  Cenário: Não deve criar board com key inválida
    Dado que possuo uma key inválida
    Quando eu tentar criar um board
    Então deve retornar erro 401 com mensagem "invalid key"

  # Cenários manipulando o parâmetro 'token'
  Cenário: Não deve criar board sem token
    Dado que não informo token
    Quando eu tentar criar um board
    Então deve retornar erro 401 com mensagem "missing scopes"

  Cenário: Não deve criar board com token inválido
    Dado que possuo um token inválido
    Quando eu tentar criar um board
    Então deve retornar erro 401 com mensagem "invalid app token"

  # Cenários manipulando o parâmetro 'name'
  Cenário: Não deve criar board sem nome
    Dado que possuo uma key e token válidos
    Quando eu criar um board sem nome
    Então deve retornar erro 400

  Cenário: Criar board com nome mínimo (1 caractere)
    Dado que possuo uma key e token válidos
    Quando eu criar um board com nome "A"
    Então o board deve ser criado com sucesso

  Cenário: Criar board com nome no limite máximo (16384 caracteres)
    Dado que possuo uma key e token válidos
    Quando eu criar um board com nome de 16384 caracteres
    Então deve retornar erro de tamanho de requisição

  Cenário: Não deve criar board com nome acima do limite
    Dado que possuo uma key e token válidos
    Quando eu criar um board com nome de 16385 caracteres
    Então deve retornar erro de tamanho de requisição

  # Cenários manipulando o parâmetro 'permissionLevel'
  Esquema do Cenário: Criar board com permissionLevel válido
    Dado que possuo uma key e token válidos
    Quando eu criar um board com permissionLevel "<valor>"
    Então o board deve ser criado com sucesso

    Exemplos:
      | valor   |
      | private |
      | public  |
      | org     |

  Cenário: Não deve criar board com permissionLevel inválido
    Dado que possuo uma key e token válidos
    Quando eu criar um board com permissionLevel "invalid"
    Então deve retornar erro 400

  # Cenários manipulando o parâmetro 'defaultLabels'
  Esquema do Cenário: Criar board com defaultLabels válido
    Dado que possuo uma key e token válidos
    Quando eu criar um board com defaultLabels "<valor>"
    Então o board deve ser criado com sucesso

    Exemplos:
      | valor |
      | true  |
      | false |

  Cenário: Não deve aceitar defaultLabels inválido
    Dado que possuo uma key e token válidos
    Quando eu criar um board com defaultLabels "abc"
    Então deve retornar erro 400