# Spring Cloud (Eureka, Config Service) Projeto antigo, algumas coisas já foram descontinuadas do spring.

Neste exemplo é para demonstrar e registrar uma estrutura de microsservicos, e como a tecnologia Spring cloud suprta e orquestra os microsserviços, o exemplo é de uma loja de flores onde o cliente autentica e faz um pedido na loja, que delega o pedido para o fornecedor e para o entregador, execitando o fall-back, load-balance, pool de thread e o request usando Feing.

A titulo de exemplo o fornecedor e transportador está na mesma estrutura, somente para entender o comportamento de vários micro-services se comporta com Eureka service, na vida real esses serviços estará em estruturas diferente e até desconhecida.

# Discovery Eureka Service

Todos serviços que utiliza a dependencia maven Eureka client em sua configuração se registra no server informando o seu nome, que identificará o serviço, para comunicação, sem que seja preciso saber IP e porta.

# Config Server

Este server, encapsula todas as configurações especificas de cada serviço, tais como a de banco de dados, podendo ser guardado em repositório local ou no git, identificando o serviço pelo seu nome.

Essa funcionalidade inicia antes do inicio dos microsserviços, primeiro ele busca essas informações, quando tem um arquivo botstrep.yml, depois segue com o inicio dos microsserviços já com as configurações do spring JPA com as configurações de banco ou qualquer outra configuração.

# API Gateway ZUUL

É um client do Eureka, registra e obtem informações de nomes de serviços ativos, e recebe requisições somente de usuarios autenticados via token OAuth2.

# Serviço Oauth2

Responsavel por toda segurança de todo ecosistema de microservice, Autentica e valida o token.

# Observability
Os logs com spring sleuth, os logs dos microsserviços são centralizados através de um ID de corelação(traceid), conseguimos rastrear o log da transação utilizando o paperTrail.

# Load Balance

É administrado pelo FeignClient, que com sua tecnologia, obtem as instancias no Eureka.

# Fall-back

É administrado pelo Hystrix, a nivel de método com default e o time-out de 1s.

# Pool de Thread

Tambem administrado pelo Hystrix, dando um id, a nivél de metodo, fazendo com que não fique indisponivel devido uma lentidão pontual do serviço.

# circuit-Breaker

Dado um número de falhas, o Hystrix coomeça a direcionar para o FAll BACK, e vai testando o fluxo normal, até que volte a funcionar.

# Microsserviços

Todos deste exemplo, são bem simples, utiliza o config-service, a gravação dos logs com spring sleuth, são registrado no Eureka e todos utiliza security OAuth2.

Existe um desenho da raiz do projeto para ajudar vizualiar os cenários.
