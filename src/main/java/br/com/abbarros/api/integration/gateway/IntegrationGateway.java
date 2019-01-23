package br.com.abbarros.api.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import br.com.abbarros.api.model.CompositeCriteria;
import br.com.abbarros.api.model.CompositeResult;

@MessagingGateway(defaultRequestChannel = "searchRequest", defaultReplyChannel = "searchAggregatedReply", defaultReplyTimeout = "20000")
public interface IntegrationGateway {
	
	@Gateway(requestChannel = "searchRequest", replyChannel = "searchAggregatedReply")
	CompositeResult search(CompositeCriteria criteria);

}
