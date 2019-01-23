package br.com.abbarros.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.abbarros.api.integration.gateway.IntegrationGateway;
import br.com.abbarros.api.model.CompositeResult;
import br.com.abbarros.api.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class SpringBootIntegrationApplicationTests {
	
	@Autowired
	IntegrationGateway searchRequestor;
	
	@Autowired
	SearchA searchA;
	
	@Autowired
	SearchB searchB;
	
	@Test
    public void testSearch() throws Exception {
		searchA.setExecutionTime(1000L);
		searchB.setExecutionTime(10000L);
		CompositeResult result = searchRequestor.search(TestUtils.getCompositeCriteria());
		assertEquals(2, result.getResults().size());
	}
	
}

