package br.com.abbarros.api.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CompositeResult extends Result {
	
	private Collection<Result> results = new ArrayList<Result>();

}
