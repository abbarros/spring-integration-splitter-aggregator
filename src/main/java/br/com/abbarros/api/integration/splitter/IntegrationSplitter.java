package br.com.abbarros.api.integration.splitter;

import java.util.Collection;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;

import br.com.abbarros.api.model.AbstractCriteria;
import br.com.abbarros.api.model.CompositeCriteria;

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
