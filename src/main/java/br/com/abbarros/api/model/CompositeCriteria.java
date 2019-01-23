package br.com.abbarros.api.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CompositeCriteria extends AbstractCriteria {

	private final Collection<AbstractCriteria> criteria = new ArrayList<AbstractCriteria>();

}
