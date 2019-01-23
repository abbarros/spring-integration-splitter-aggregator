package br.com.abbarros.api.integration.activator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;

import br.com.abbarros.api.SearchA;
import br.com.abbarros.api.model.CriteriaA;
import br.com.abbarros.api.model.Result;

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
