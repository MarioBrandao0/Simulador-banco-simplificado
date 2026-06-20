# Simulador de banco simplificado
___
A ideia principal desse projeto é desenvolver um simulador de banco simplificado.

## Objetivo
Este é um projeto pessoal de estudo, criado para praticar conceitos de back-end como modelagem de domínio, regras de negócio, transações atômicas, integração com serviços externos e tratamento de erros.

> Projeto em desenvolvimento. Funcionalidades e entidades ainda estão sendo implementadas.

## Funcionalidades planejadas
- [X] Cadastro de usuários comuns e lojistas
- [X] Validação de CPF e e-mails únicos
- [ ] Transferência de dinheiro entre usuários
- [ ] Restrição: lojistas apenas recebem, não enviam dinheiro
- [ ] Consulta a um serviço externo de autorização antes de concluir a transferência
- [ ] Reversão automática da transação em caso de falha
- [ ] Notificação assíncrona ao destinatário após receber uma transferência

## Regras de negócio
- Usuários possuem nome completo, CPF, e-mail e senha
- Existem dois tipos de usuário: **comum** e **lojista**
- Lojistas só podem **receber** transferências
- Toda transferência passa por validação de saldo
- Antes de finalizar, o sistema consulta um serviço externo simulado de autorização
- Em caso de inconsistência, a operação é revertida por completo

## Status do projeto
Em desenvolvimento — Tranferencia de dinheiro entre usuários junto com a restrição dos lojistas.

Feito - Criação e listagem de usuários.

## Como rodar
Calma, jovem Padawan. Ainda estou desenvolvendo — esse é só o commit inicial.