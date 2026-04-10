# language: pt

Funcionalidade: Delete de Card

Cenario: Excluir um card válido
Dado que possuo um board válido
Quando eu buscar listas do board
E eu criar um card válido
Quando eu deletar o card
Entao deve retornar status 200

Cenario: Excluir um card inexistente
Quando eu deletar um card inexistente
Entao deve retornar status 400