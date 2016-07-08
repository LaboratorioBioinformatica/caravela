package br.usp.iq.lbi.caravela.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOImpl<T> implements DAO<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOImpl.class);

	protected EntityManager entityManager;
	
	private Class<T> entityClass;
	
	private static final int MAX_NUMNER_OF_TRANSACTION = 100000;
	private Long numberOfTrasaction = 1L;
	private Long numberOfBatch = 1L;
	

	@SuppressWarnings("unchecked")
	public DAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
	    this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	public void addBatch(T entity, Integer batchSize) {
		entityManager.persist(entity);
		managerMaxNumberOfTransaction(batchSize);
	}
	
	
	public void addBatch(T entity) {
		entityManager.persist(entity);
		managerMaxNumberOfTransaction(MAX_NUMNER_OF_TRANSACTION); 

	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		return entityManager.createQuery("from " + entityClass.getName()).getResultList();
	}
	
	public T load(Long id) {
		if(id != null){
			return entityManager.find(entityClass, id);
		} else {
			return null;
		}
	}
	
	public void save(T entity) {
		entityManager.persist(entity);
	}
	
	
	public void delete(T entity){
	  entityManager.remove(entity);
   }
	
	public T update(T entity){
		return entityManager.merge(entity);
	}

	public T load(T entity) {
		Long idValue = 0L;
		try {
			idValue = (Long) entityClass.getMethod("getId").invoke(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		return load(idValue);
	}
	
	private void managerMaxNumberOfTransaction(Integer maxNumnerOfTransaction) {
		if (numberOfTrasaction >= maxNumnerOfTransaction) {
			entityManager.flush();
			entityManager.clear();
			numberOfTrasaction = 0L;
//			System.out.println("Reset number of transaction. Batch number: " + numberOfBatch);
			numberOfBatch++;
		} else {
			numberOfTrasaction++;
		}
	}

	public Long count() {
		return (Long) entityManager.createQuery("SELECT COUNT(e) FROM "+ entityClass.getName() + " e", Long.class).getSingleResult();
	}

}
