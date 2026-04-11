# language: pt

@All
Funcionalidade: Fluxo E2E visual de card

  Cenario: Criar, mover e deletar card em board fixo

    Dado que utilizo um board fixo E2E
    Quando eu busco as listas do board E2E
    E eu crio um card E2E
    Então o card E2E deve ser criado com sucesso

    Quando eu movo o card entre listas E2E
    Então o card deve ser movido entre as listas

    Quando eu removo o card E2E
    Então o card E2E nao deve mais existir