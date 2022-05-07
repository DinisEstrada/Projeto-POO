# Projeto-POO
Projeto POO | 2ºano | 2ºSemestre | Universidade do Minho 2021/2022

 
### SmartDevice
É a super classe que integra SmartBulb,SmartSpeaker e SmartCamera( Resolução integra smartcamera como v.i.) como subclasses.
Tem como v.i. comuns:
- Id : dispositivo
- Estado : ON/OFF (true/false)
- custo_instalacao
- consumo-diario (inicializado sempre a zero e só atribuido quando se inicializa as v.i. das subclasses pois cada sub tem formulas de consumo diferentes)
Depois cada subclasse adiciona as v.i. especificas


### Casa Inteligente
É a classe que guarda todos os smartdevices e as divisoes onde se encontram. as variaveis de instancia são
- dono
- NIF
- Hashmap de todos devices. key= id do device, aponta para o smartdevice
- Hashmap das divisoes, key: nome da divisao, aponta para lista de ids de smartdevices nessa divisão

### Fornecedor 
É uma classe abstrata que constroi 3 tipos de fornecedores(A/B e C). Usei classe abstrata pois o metodo formulaPreco tem formula diferente em cada Fornecedor.
V.I. sao as variaveis da formula
-nome
-valor_base
-imposto
-desconto

clone e tostring tambem são abstratos para funcionarem direito.

### Fatura 
Classe que tira faturas de uma casa,para isso conta com as variaveis de instancia:
- casa: nome do prop
- data inicio (formato da data String : "dd-mm-yyyy")
- data de fim
- Consumo durantem o periodo
- valor da fatura durante o perido
para tirar fatura é so ter uma casa criada e fazer casa.faturaCasa(inicio, fim);


### Parser
Objetivo é receber um logs.csv e construir tudo de forma automática. A estrutura do config pode ser vista nos avisos da bb
V.I. é o ficheiro.
metodos importantes:
 - houseConfig: gera um hashmap de todas as casas inteligentes. Key= nome da casa dada no ficheiro |-> CasaInteligente
 - energyConfig : gera um hashmap de todos os fornecedores e as suas casas. Key= nomedacasa |->  Fornecedor

### Package Error Handling 
Tem todas as subclasses que extendem Exception de modo a mandar mensagens de erro com alguma granularidade.
Todas os metodos das classes fazem "throw" dos erros para serem todos colecionados na main e imprimirem a mensagem;
Também se consegue controlar o input de valores não válidos, assim como, assegurar que metodos funcionam todos com os valores corretos ou dão erro e não executam. 


### Tarefas já feitas:
- Criar classe smartdevice e as suas subclasses (Zé)
- Criar a classe casa Inteligente (Zé)
- Arranjar forma de criar fornecedores (Zé)
- Parser do ficheiro e criação automática da configuração de casas e smartdevices (Zé)
- Error Handling e como Guardar informação num ficheiro (Zé)
- tirar faturas por cada casa (prov criar classe fatura) (Zé)
- Criar uma classe estado com todas as casas e fornecedores e as suas configurações (Zé)
- fazer metodos de estatisticas (Zé)
  - casa que gastou mais
  - fornecedor com maior volume de faturação
  - todas as faturas por fornecedor
  - casas com maior consumo durante X tempo
- Permitir mudanças durante a simulação (todas as alterações refletem na fatura seguinte) (zé)\
  *__Comentario__: isto é uma combinação dos menus com a mudança de estado. a mudança do estado já está implementada - falta adicionar a interface disto*
  - mudar de fornecedor
  - ligar e desligar dispositivos
  - mudar valores praticados pelo fornecedor (valor_base,desconto,imposto)
 
### Tarefas por fazer:
- MENU e SUBMENUS (DINIS)
- executar uma simulação (calculo do consumo na evolução temporal de um estado)
- Automatizar as mudanças com um parser de informação
  - ideia: pede-se o periodo de faturação e entre ele criar mini faturas entre cada mudança de estado. guarda-se as mini faturas e depois somam-se todas.
- [Extra] Criar ficheiros de testes Casa-SmartDevice-Fornecedor- /*automatizar processo de testagem*/
- [Extra] Verificar se esta tudo por composição. 

## COISAS A CORRIGIR
- quando os sores mandarem o ficheiro logs direito corrigir a inserção dos campos dos sumartdevices (zé-DONE)
