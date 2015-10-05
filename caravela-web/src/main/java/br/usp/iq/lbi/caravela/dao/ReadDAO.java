package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Read;

public interface ReadDAO extends DAO<Read> {
	List<Read> loadAllReadsByContig(Contig contig);

}
