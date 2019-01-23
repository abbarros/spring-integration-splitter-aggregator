package br.com.abbarros.api.integration.aggregator;

import java.util.Collection;

import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.MessageEndpoint;

import br.com.abbarros.api.model.CompositeResult;
import br.com.abbarros.api.model.Result;

@MessageEndpoint
public class IntegrationAggregator {

	@Aggregator(inputChannel = "searchReply", outputChannel = "searchAggregatedReply", sendPartialResultsOnExpiry = "true")
	public Result aggregate(Collection<Result> results) {
		CompositeResult result = new CompositeResult();
		result.getResults().addAll(results);
		return result;
	}

}
