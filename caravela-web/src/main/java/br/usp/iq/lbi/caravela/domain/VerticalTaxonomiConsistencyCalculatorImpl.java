package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class VerticalTaxonomiConsistencyCalculatorImpl implements VerticalTaxonomiConsistencyCalculator {

	@Override
	public Double calculateVTCByList(Collection<Integer> list, Integer size) {
		Double ctv = 0d;
		if( ! list.isEmpty()){
			ArrayList<Integer> listOfNumberOfBasesByTaxon = new ArrayList<Integer>(list);
			Collections.sort(listOfNumberOfBasesByTaxon);
			Integer lastElement = (listOfNumberOfBasesByTaxon.size() - 1);
			Double greaterNumberOfBasesAssignedToTaxon = 0d;
			greaterNumberOfBasesAssignedToTaxon = (double)listOfNumberOfBasesByTaxon.get(lastElement);
			ctv = greaterNumberOfBasesAssignedToTaxon / size;
			
			if(ctv.isNaN()){
				ctv = 0d;
			}
		} 
		return ctv;
	}

}
