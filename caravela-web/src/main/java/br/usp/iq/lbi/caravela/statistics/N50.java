package br.usp.iq.lbi.caravela.statistics;

import java.util.Collections;
import java.util.List;

public class N50 {
	
	
	public N50() {}
	
	public double calculate(List<Integer> lenghts){
		Collections.sort(lenghts);
		Collections.reverse(lenghts);
		int total = 0;
		int partial =0;
		int index=0;
		for(int i : lenghts){
			total+=i;
		}
		//Defined accordingly to Assemlathon 2 definition.
		//For the Assemplaton 1 definition "<="---> "<";
		while(index < lenghts.size() && partial+lenghts.get(index) <= (total/2)){
			partial += lenghts.get(index);
			index++;
		}
		return lenghts.get(index);
	}
}
