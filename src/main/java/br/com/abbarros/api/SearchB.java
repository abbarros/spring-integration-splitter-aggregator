
package br.com.abbarros.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.abbarros.api.model.CriteriaB;
import br.com.abbarros.api.model.Result;
import lombok.Data;

@Data
@Service
public class SearchB {

	private static final Log LOGGER = LogFactory.getLog(SearchB.class);

	private long executionTime = 1000L;

	public Result search(CriteriaB criteria) {

		LOGGER.info(String.format("This search will take %sms.", executionTime));

		try {
			Thread.sleep(executionTime);
		} catch (InterruptedException e) {
		}
		return new Result("Search B");
	}

}
