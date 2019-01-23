package br.com.abbarros.api.integration.activator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;

import br.com.abbarros.api.SearchB;
import br.com.abbarros.api.model.CriteriaB;
import br.com.abbarros.api.model.Result;

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
