package br.usp.iq.lbi.caravela.dao;

import java.util.List;


public interface DAO<T> {
	
	T load(Long id);
	T load(T entity);
	T update(T entity);
	Long count();
	void save(T entity);
	void addBatch(T entity);
	void addBatch(T entity, Integer batchSize);
	List<T> findAll();
	
	

}
