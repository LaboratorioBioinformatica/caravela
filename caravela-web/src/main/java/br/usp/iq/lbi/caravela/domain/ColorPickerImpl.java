package br.usp.iq.lbi.caravela.domain;

import javax.enterprise.context.RequestScoped;

import br.usp.iq.lbi.caravela.model.Taxon;

@RequestScoped
public class ColorPickerImpl implements ColorPicker {

	public String generateRandomColor(){
		String[] letters = new String[15];
		letters = "0123456789ABCDEF".split("");
		String code = "#";
		for (int i = 0; i < 6; i++) {
			double ind = Math.random() * 15;
			int index = (int) Math.round(ind);
			code += letters[index];
		}
		return code;
	}
	
	public String generateColorByTaxon(Taxon taxon){
		if (Taxon.getNOTaxon().equals(taxon)) {
			return "#A0A0A0";
		} else {
			return generateRandomColor();
		}
	}

}
