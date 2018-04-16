package com.test.customrepository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

public class DefaultRepositoryFactory extends JpaRepositoryFactory {

	public DefaultRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		Assert.notNull(entityManager);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected final <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
			RepositoryMetadata metadata, EntityManager entityManager) {

		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata
				.getDomainType());
		return new GenericRepositoryImpl(entityInformation, entityManager,
				repositoryInterface);
	}

	@Override
	protected final Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return GenericRepositoryImpl.class;
	}

}
