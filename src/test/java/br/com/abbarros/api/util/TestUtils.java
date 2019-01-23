package br.com.abbarros.api.util;

import org.springframework.stereotype.Component;

import br.com.abbarros.api.model.CompositeCriteria;
import br.com.abbarros.api.model.CriteriaA;
import br.com.abbarros.api.model.CriteriaB;

@Component
public final class TestUtils {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private TestUtils() {
	}

	public static CompositeCriteria getCompositeCriteria() {

		final CompositeCriteria generalCriteria = new CompositeCriteria();
		generalCriteria.getCriteria().add(new CriteriaA());
		generalCriteria.getCriteria().add(new CriteriaB());
		return generalCriteria;

	}
}
