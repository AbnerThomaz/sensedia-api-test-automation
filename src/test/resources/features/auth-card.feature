# language: pt

@All
Funcionalidade: Autenticacao API Card

# =========================
# TOKEN INVALIDO
# =========================

  Cenario: Criar card com token inválido
    Quando eu criar card com token inválido
    Entao deve retornar status 401

  Cenario: Atualizar card com token inválido
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizar card com token inválido
    Entao deve retornar status 401

  Cenario: Deletar card com token inválido
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu deletar card com token inválido
    Entao deve retornar status 401

# =========================
# KEY INVALIDA
# =========================

  Cenario: Criar card com key inválida
    Quando eu criar card com key inválida
    Entao deve retornar status 401

  Cenario: Atualizar card com key inválida
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizar card com key inválida
    Entao deve retornar status 401

  Cenario: Deletar card com key inválida
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu deletar card com key inválida
    Entao deve retornar status 401

# =========================
# SEM TOKEN
# =========================

  Cenario: Criar card sem o parametro token
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar card sem passar o token
    Entao deve retornar status 401

  Cenario: Atualizar card sem o parametro token
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizar card sem passar o token
    Entao deve retornar status 401

  Cenario: Deletar card sem o parametro token
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu deletar card sem passar o token
    Entao deve retornar status 401

# =========================
# SEM KEY
# =========================

  Cenario: Criar card sem o parametro key
    Dado que possuo um board válido
    Quando eu buscar listas do board
    Quando eu criar card sem passar o key
    Entao deve retornar status 401

  Cenario: Atualizar card sem o parametro key
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizar card sem passar o key
    Entao deve retornar status 401

  Cenario: Deletar card sem o parametro key
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu deletar card sem passar o key
    Entao deve retornar status 401

# =========================
# KEY EM BRANCO
# =========================

  Cenario: Criar card passando em branco o parametro key
    Dado que possuo um board válido
    Quando eu buscar listas do board
    Quando eu criar card passando em branco o key
    Entao deve retornar status 401

  Cenario: Atualizar card passando em branco o parametro key
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizar card passando em branco o key
    Entao deve retornar status 401

  Cenario: Deletar card passando em branco o parametro key
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu deletar card passando em branco o key
    Entao deve retornar status 401

# =========================
# TOKEN EM BRANCO
# =========================

  Cenario: Criar card passando em branco o parametro token
    Dado que possuo um board válido
    Quando eu buscar listas do board
    Quando eu criar card passando em branco o token
    Entao deve retornar status 401

  Cenario: Atualizar card passando em branco o parametro token
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu atualizar card passando em branco o token
    Entao deve retornar status 401

  Cenario: Deletar card passando em branco o parametro token
    Dado que possuo um board válido
    Quando eu buscar listas do board
    E eu criar um card válido
    Quando eu deletar card passando em branco o token
    Entao deve retornar status 401