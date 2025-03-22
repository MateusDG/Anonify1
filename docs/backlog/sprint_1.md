
## 1. Objetivo Geral da Sprint 1

 **Entregar** :

* Um primeiro protótipo funcional do chat via Tor (cliente/servidor .onion),
* Um planejamento de como as tarefas foram (ou estão sendo) desenvolvidas,
* Um documento inicial de requisitos (funcionais e não funcionais).

Ao final da Sprint, todos entendem o que o sistema faz, como ele faz, e quais são os requisitos que estão direcionando o desenvolvimento.

---

## 2. Modelo de Processo

### 2.1 Metodologia

* **Iterativo/Incremental**: Vamos trabalhar em ciclos curtos (Sprints) e, a cada ciclo, entregar algo que já funcione parcialmente.
* **Scrum**: Um ou dois *sprints* focados em consolidar funcionalidades essenciais e requisitos.

### 2.2 Papéis (ou Responsáveis)

* **Desenvolvedor(a) Backend**:
  * Implementa lógica Tor (TorServerService, TorClientService) e integrações no `Services`.
  * Configura portas, parâmetros, leitura do arquivo `.onion`, etc.
* **Desenvolvedor(a) Frontend/UI**:
  * Cuida da interface gráfica (ChatPanel, Painéis de configuração, Layout e design).
  * Garante a coerência visual, estilos, usabilidade.

---

## 3. Atividades Principais da Sprint 1

1. **Configurar e Documentar o Ambiente**
   * Explicar como rodar o projeto localmente.
   * Explicar como instalar/configurar Tor (para rodar servidor .onion e conectar-se a ele).
   * Resultado esperado: *Guia rápido* no README.md ou em um doc interno.
2. **Desenvolver (ou Finalizar) a Lógica do Servidor e Cliente Tor**
   * Classes: `TorServerService` (configuração do servidor .onion) e `TorClientService` (conexão via proxy Tor).
   * Ajustar tratamento de exceções, ler arquivo do `hidden_service/hostname`, iniciar servidor na porta correta etc.
3. **Implementar (ou Refinar) a UI do Chat**
   * Classes: `ChatPanel`, `InputPanel`, `LogoPanel`, `TabManager`, e demais componentes que vocês já criaram.
   * Validar que a troca de mensagens funciona de ponta a ponta: o que digita no `InputPanel` aparece no `ChatPanel` e viaja até o servidor/cliente Tor.
4. **Organizar o Projeto em Pacotes**
   * `com.anonify.services` para classes de backend/serviços,
   * `com.anonify.ui` e subpacotes (`.components`, `.panels`) para a interface,
   * `com.anonify.utils` para utilitários (ex.: `MessageUtils`, `Constants`).
   * Deixar claro o propósito de cada pasta/arquivo.
5. **Criação do Documento de Especificação de Requisitos**
   * **Requisitos Funcionais** : “Servidor onion iniciando na porta X”, “Cliente conecta via .onion”, “Interface exibe mensagens” etc.
   * **Requisitos Não Funcionais** : “Foco na segurança/anonimato (uso de Tor)”, “Interface responsiva e amigável”, “Performance aceitável”.
   * Critérios de aceitação mínimos (por ex.: “Ao clicar no botão  *Connect* , deve conectar ou exibir mensagem de erro”).
6. **Teste e Demonstração Interna**
   * Subir o servidor .onion local, conectar-se com o cliente, mandar mensagem “hello world” e verificar logs.
   * Se possível, ter outra pessoa da equipe conectando de outro PC para validar envio/recebimento real.
   * Registrar em *issues* ou *tarefas* possíveis melhorias.

---

## 4. Cronograma

| Semana / Dia                 | Atividade                                                                                                                                         | Responsáveis                     | Observações                                                                |
| ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------- | ---------------------------------------------------------------------------- |
| **Semana 1 - Dia 1**   | - Planejamento e Kick-off da Sprint  - Reunião: alinhar objetivos e passos                                                                       | Todos                             | Deixar claro o foco: “Entregar chat Tor inicial + doc de requisitos”       |
| **Semana 1 - Dia 2-3** | - Configurar ambiente e Tor  - Documentar (README ou Wiki) como rodar o projeto                                                                   | Desenvolvedor(a) Backend + Equipe | Para quem for clonar, ter guia simples “como subir o servidor onion”       |
| **Semana 1 - Dia 4-5** | - Consolidar classes do servidor/cliente (`TorServerService`,`TorClientService`,`Services`)  - Revisar exceções, logs e mensagens do chat | Desenvolvedor(a) Backend          | Ver se a lógica de*socket* , *proxy* ,*endereço onion*está em ordem |
| **Semana 2 - Dia 1**   | - Integrar UI com backend  - Ajustar estilos e fluxos nos painéis (ChatPanel, ConnectOnionPanel, etc.)                                           | Desenvolvedor(a) Frontend/UI      | Ver se “Start Server” e “Connect” chamam de fato a*lógica backend*    |
| **Semana 2 - Dia 2-3** | - Criação do Documento de Requisitos (fun + não fun)  - Ajuste final de pacotes e organização                                                | Todos                             | Requisito: ter um*doc*simples em .md ou .docx                              |
| **Semana 2 - Dia 4**   | - Testes iniciais de envio/recebimento de mensagens  - Correções rápidas                                                                       | Backend + Frontend/UI             | Time foca em QA básico; anotar*bugs*e*melhorias*                        |
| **Semana 2 - Dia 5**   | -*Review*da Sprint (mostrar chat rodando e doc de requisitos)  - *Retrospectiva* : O que melhorar no processo?                                | Todos                             | Encerrar a sprint formalmente                                                |

---

## 5. Esqueleto do Documento de Especificação de Requisitos

1. **Introdução**
   * Descrição geral do projeto: “Chat anônimo via Tor”.
   * Contexto: “Criado do zero para aprendizado e uso de Java + Swing + Tor”.
2. **Requisitos Funcionais** (Exemplos)
   * **[RF001]** Iniciar servidor .onion com a porta definida e exibir o endereço onion.
   * **[RF002]** Conectar via proxy Tor a um endereço onion, podendo trocar mensagens.
   * **[RF003]** Exibir interface gráfica amigável, com layout em abas (Settings, Chat).
3. **Requisitos Não Funcionais** (Exemplos)
   * **[RNF001]** Utilizar rede Tor para manter anonimato (não expor IP).
   * **[RNF002]** Interface “dark theme” + rolagem customizada.
   * **[RNF003]** Código organizado em pacotes (services, ui, utils) para facilitar manutenção.
4. **Critérios de Aceitação**
   * “Quando o usuário clicar em  *Start Server* , deve surgir uma janela `ServerStatusWindow` com a mensagem *Server Running* e o endereço onion.”
   * “Quando o usuário conectar a um .onion inválido, deve receber feedback no *ChatPanel* indicando erro.”
5. **Versões / Histórico**
   * Sprint 1: Versão inicial, servidor e cliente funcionando localmente.

---

## 6. Fechamento da Sprint 1

Ao finalizar esta Sprint, você deve ter:

* **Um protótipo funcional** (mesmo que básico) onde:
  1. O servidor .onion é iniciado em uma porta.
  2. O cliente conecta, enviando/recebendo mensagens em tempo real.
  3. A interface exibe as mensagens nas “bolhas” customizadas do ChatPanel.
* **Um documento de requisitos** (pode ser um Markdown no Git) descrevendo o que foi implementado ou planejado.
* **Tarefas abertas** (no Trello/Jira/GitHub Issues ou outra ferramenta) para itens pendentes ou melhorias para a próxima Sprint.
