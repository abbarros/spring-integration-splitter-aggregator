# Spring Integration with Splitter/Aggregator

Demonstração de como implementar os ```Enterprise Integration Patterns (EIP) do Splitter / Aggregator``` usando ```Spring Integration```. Este exemplo fornece uma demonstração de solicitação-resposta, dividindo uma mensagem e, em seguida, agregando as respostas. Além disso, esta amostra está processando as mensagens divididas simultaneamente. 

O projeto foi desenvolvido em ```Spring Boot``` com base no [Splitter/Aggregator with Reaper Sample](http://github.com/spring-projects/spring-integration-samples/tree/master/intermediate/splitter-aggregator-reaper "Splitter/Aggregator with Reaper Sample") 

## Arquitetura
![Arquitetura spring-integration-splitter-aggregator](/src/main/resources/docs/splitter-aggregator.png)

### Gateway
O Gateway é um componente com objetivo principal do ocultar a API do sistema de mensagens fornecida pelo Spring Integration, permite que a lógica de negócios do seu aplicativo fique separada da API do Spring Integration. Basicamente, o IntegrationGateway posta o nosso objeto no canal ```searchRequest``` e aguarda a resposta da mensagem no canal ``` searchAggregatedReply ```.
```java
@MessagingGateway(defaultRequestChannel = "searchRequest", defaultReplyChannel = "searchAggregatedReply", defaultReplyTimeout = "20000")
public interface IntegrationGateway {
	
	@Gateway(requestChannel = "searchRequest", replyChannel = "searchAggregatedReply")
	CompositeResult search(CompositeCriteria criteria);

}
```
### Splitter/Router
O Splitter é um componente cuja função é particionar uma mensagem em várias partes e enviar as mensagens resultantes para serem processadas independentemente. Basicamente, o IntegrationSplitter recebe a mensagem postada pelo Gateway no canal ``` searchRequest ```, faz o splitter e roteia as mensagens entre os Service Activator.
```java
@MessageEndpoint
public class IntegrationSplitter {
	
	@Splitter(inputChannel="searchRequest", outputChannel = "searchRequests")
	public Collection<AbstractCriteria> split(CompositeCriteria criteria) {
		return criteria.getCriteria();
	}
	
	@Router(inputChannel="searchRequests")
	public String router(Message<?> message) {
		switch (message.getPayload().getClass().getSimpleName()) {
			case "CriteriaA" :
				return "searchRequestA";
			case "CriteriaB" :
				return "searchRequestB";
			default:
	            return "";
		}
	}

}
```
### Service Activator
O Service Activator é um tipo de terminal para conectar qualquer objeto gerenciado pelo Spring a um canal de entrada para que ele possa desempenhar o papel de um serviço. Basicamente, o ServiceActivatorA e ServiceActivatorB recebem as mensagens postadas pelo IntegrationRouter executa a lógica de cada search e posta a resposta no canal ``` search-reply ```.
```java
@MessageEndpoint
public class ServiceActivatorA {
	
	@Autowired
	SearchA searchA;
	
	@ServiceActivator(inputChannel = "searchRequestA", outputChannel = "searchReply", poller = 
			@Poller(maxMessagesPerPoll = "1", fixedDelay = "10"))
	public Result search(CriteriaA criteria) {
		return searchA.search(criteria);
	}

}
```
```java
@MessageEndpoint
public class ServiceActivatorB {
	
	@Autowired
	SearchB searchB;
	
	@ServiceActivator(inputChannel = "searchRequestB", outputChannel = "searchReply", poller = 
			@Poller(maxMessagesPerPoll = "1", fixedDelay = "10"))
	public Result search(CriteriaB criteria) {
		return searchB.search(criteria);
	}

}
```
### Aggregator
O Agregador é um tipo de manipulador de mensagens que recebe várias mensagens e as combina em uma única. Basicamente, o TravelInsuranceAggregator recebe as mensagens postadas pelos Service Activator no canal ``` searchReply ```, usa o CORRELATION_ID default gerado no cabeçalho da mensagem para a agregagação - é possível escrever estratégias de correlação customizadas, estratégias de liberação e armazenamentos de mensagens podem ser injetadas em um conjunto de aspectos de configuração avançados - , e posta no canal ``` searchAggregatedReply ``` que é o canal de output do IntegrationGateway.
```java
@MessageEndpoint
public class IntegrationAggregator {

	@Aggregator(inputChannel = "searchReply", outputChannel = "searchAggregatedReply", sendPartialResultsOnExpiry = "true")
	public Result aggregate(Collection<Result> results) {
		CompositeResult result = new CompositeResult();
		result.getResults().addAll(results);
		return result;
	}

}
```
### Canal
Um canal no Spring Integration (e, na verdade, EAI) é o encanamento básico em uma arquitetura de integração, é por ele na qual as mensagens são retransmitidas de um sistema ou componente para outro.
```java
@Configuration
public class ChannelConfiguration {
	
	@Bean
	public MessageChannel searchRequest() {
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel searchAggregatedReply() {
		return new DirectChannel();
	}
	
	@Bean
	public MessageChannel searchRequests() {
		return new DirectChannel();
	}
	
	@Bean
	public QueueChannel searchRequestA() {
		return new QueueChannel(10);
	}
	
	@Bean
	public QueueChannel searchRequestB() {
		return new QueueChannel(10);
	}
	
	@Bean
	public PublishSubscribeChannel searchReply() {
		return new PublishSubscribeChannel();
	}

}
```
## Build do Projeto
Execute a linha de comando maven:
```sh
mvn clean package
```

## Teste Unitário
Execute a linha de comando maven:
```sh
mvn test
```

## Rodar a Aplicação
Execute a linha de comando maven:
```sh
mvn spring-boot:run
```
```


