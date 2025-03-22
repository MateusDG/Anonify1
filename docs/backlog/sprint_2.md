## 1. Objetivos da Sprint 2

1. **Detalhar requisitos funcionais** por meio de:
   * **Histórias de Usuário** (User Stories) que descrevem a perspectiva do usuário final.
   * **Cenários de Teste** (ou Critérios de Aceitação) que ajudam a validar cada história.
2. **Definir o Projeto Arquitetural** do sistema, incluindo:
   * **Diagrama de Classes** UML, mostrando as principais classes (por exemplo, TorServerService, TorClientService, ChatPanel, etc.) e suas relações.
   * **Diagrama de Componentes** , ilustrando os módulos/layers (por exemplo,  *services* ,  *ui* ,  *utils* ) e como se comunicam.
   * **Diagrama de Sequência** para demonstrar o fluxo de mensagens entre as classes mais relevantes (por exemplo, o passo a passo de “Start Server” ou “SendMessage”).

Esses artefatos vão **guiar as próximas evoluções** do projeto e facilitar que o time entenda (ou relembre) como o sistema funciona de ponta a ponta.

---

## 2. Escopo Principal de Tarefas

### 2.1 Detalhar Requisitos Funcionais com Histórias de Usuário

1. **Identificar os atores (usuários)**

   * Exemplo: “Usuário que hospeda o servidor onion”, “Usuário que se conecta ao servidor onion”, etc.
2. **Escrever histórias de usuário** usando um formato como:

   > **Como** [tipo de usuário], **eu quero** [ação/funcionalidade], **para** [benefício/razão].
   >

   * Exemplo 1 (Host): “**Como** administrador do chat, **eu quero** iniciar meu servidor .onion em uma porta específica, **para** permitir que outros usuários conectem-se anonimamente.”
   * Exemplo 2 (Cliente): “**Como** participante do chat, **eu quero** conectar a um endereço .onion utilizando o proxy Tor, **para** trocar mensagens sem revelar meu IP.”
   * Exemplo 3 (Usuário Geral): “**Como** usuário final, **eu quero** digitar uma mensagem na interface e enviá-la ao servidor, **para** que todos os participantes do chat a recebam.”
3. **Associar critérios de aceitação / cenários de teste** a cada história

   * Podem ser descritos em formato **Dado-Quando-Então** (Gherkin) ou simplesmente em  *checklist* .
   * Exemplo (para a história “iniciar servidor .onion”):
     * **Dado** que estou no painel  *ConfigureOnionPanel* ,
     * **Quando** preencho a porta “12345” e clico em “Start Server”,
     * **Então** o sistema deve exibir o endereço onion lido do arquivo `hostname` e mostrar a mensagem “Server Running” na janela de status.

Esses cenários de teste servirão depois como base para testes manuais ou automatizados.

---

### 2.2 Definir o Projeto Arquitetural (UML)

1. **Diagrama de Classes**

   * Mapa de classes principais e seus relacionamentos (associações, dependências etc.):
     * **Services** (fachada para iniciar/encerrar servidor, conectar cliente, enviar mensagens)
     * **TorServerService** e **TorClientService** (responsáveis pela comunicação via sockets e proxy Tor)
     * **ChatPanel, InputPanel, LogoPanel, TabManager** (componente de UI)
     * **MessageUtils, Constants** (utilitários)

   Um diagrama de classes (simplificado) poderia mostrar:

   * `Services` depende de `TorServerService` e `TorClientService`.
   * `TorServerService` e `TorClientService` usam `ChatPanel` apenas para exibir mensagens (ou usam callback/evento para atualização de interface).
   * `UI` ou `MainFrame` agregam instâncias de `ChatPanel`, `InputPanel`, etc.
2. **Diagrama de Componentes**

   * Ilustra como o sistema está dividido em módulos/pacotes:
     * **com.anonify.services** (módulo de backend/servidor/cliente Tor),
     * **com.anonify.ui** (módulo de interface, subdividido em `.components`, `.panels`),
     * **com.anonify.utils** (módulo de utilitários).
   * As setas/conexões indicam quais módulos dependem de quais.
3. **Diagrama de Sequência**

   * Mostrar o fluxo para **1) Iniciar o servidor** e **2) Enviar mensagem** (por exemplo):
   * **Iniciar servidor** :

   1. `ConfigureOnionPanel` chama `Services.startOnionServer(...)`
   2. `Services` chama `TorServerService.main(...)`
   3. `TorServerService` lê o arquivo do hidden_service, inicializa `ServerSocket`, aguarda conexões...
   4. Retorna endereço onion para `Services`, que exibe no `ChatPanel` e abre `ServerStatusWindow`.

   * **Enviar mensagem** (cliente):
     1. Usuário digita texto em `InputPanel` e clica em “Send”.
     2. `InputPanel` chama `torSendMessage.sendMessageToServer(message);`
     3. `Services` delega para `TorClientService.sendMessage(...)`
     4. `TorClientService` envia mensagem via `Socket` para o servidor.
     5. `TorServerService` recebe, repassa para os demais clientes.

Esses diagramas podem ser feitos em ferramentas UML (Astah, Visual Paradigm, StarUML, ou mesmo diagramas em Markdown com mermaid).

---

## 3. Organização de Tarefas na Sprint 2

Sugestão de **duas semanas de Sprint** :

| Semana / Dia                 | Atividade                                                                                                             | Responsáveis                             |
| ---------------------------- | --------------------------------------------------------------------------------------------------------------------- | ----------------------------------------- |
| **Semana 1 - Dia 1**   | **Planejamento e Kick-off da Sprint**- Reunir equipe, explicar objetivo: “detalhar requisitos + UML”          | Todos                                     |
| **Semana 1 - Dia 2-3** | **Escrever Histórias de Usuário**- Para cada funcionalidade central (start server, connect, chat, etc.)       | PO/Líder de Produto + Backend/Frontend   |
| **Semana 1 - Dia 4**   | **Definir Cenários de Teste**- Associar “Dado-Quando-Então” ou checklists a cada história                  | QA ou todos (se time pequeno)             |
| **Semana 1 - Dia 5**   | **Criar/atualizar Diagrama de Classes**- Representar principais entidades do código                            | Desenvolvedor(es) com conhecimento global |
| **Semana 2 - Dia 1**   | **Criar Diagrama de Componentes**- Mostrar módulos (services, ui, utils) e relações                          | Backend + Frontend                        |
| **Semana 2 - Dia 2**   | **Criar Diagrama de Sequência**- Pelo menos 2 fluxos importantes (start server, send message)                  | Backend + Frontend                        |
| **Semana 2 - Dia 3-4** | **Revisão Interna dos Artefatos**- Ajustar detalhes, garantir que as histórias fazem sentido                  | Todos                                     |
| **Semana 2 - Dia 5**   | **Sprint Review**- Apresentar as histórias, cenários de teste e diagramas UML**Retrospectiva**da Sprint | Todos                                     |

---

## 4. Entregáveis Principais ao Final da Sprint 2

1. **Documento de Requisitos Atualizado**

   * Contendo **Histórias de Usuário** e **cenários de teste** .
   * Pode ser em formato Markdown no repositório ou doc compartilhado.
2. **Diagramas UML**

   * **Classe**: mostrando como as principais classes do seu código se relacionam.
   * **Componentes**: ilustrando a arquitetura em alto nível (pacotes e dependências).
   * **Sequência**: exemplificando fluxos de uso fundamentais.
