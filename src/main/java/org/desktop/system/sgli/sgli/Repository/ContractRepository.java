package org.desktop.system.sgli.sgli.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.desktop.system.sgli.sgli.Entity.ContractModel;

import java.util.List;
import java.util.UUID;

public class ContractRepository {

    public List<ContractModel> findAll() {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            return entityManager
                    .createQuery("SELECT contract FROM ContractModel contract ORDER BY contract.nameLocatario", ContractModel.class)
                    .getResultList();
        }
    }

    public ContractModel save(ContractModel contract) {
        return executeInTransaction(entityManager -> {
            entityManager.persist(contract);
            return contract;
        });
    }

    public ContractModel update(ContractModel contract) {
        return executeInTransaction(entityManager -> entityManager.merge(contract));
    }

    public void delete(UUID id) {
        executeInTransaction(entityManager -> {
            ContractModel contract = entityManager.find(ContractModel.class, id);
            if (contract != null) {
                entityManager.remove(contract);
            }
            return null;
        });
    }

    private <T> T executeInTransaction(JpaOperation<T> operation) {
        EntityTransaction transaction = null;

        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            T result = operation.execute(entityManager);

            transaction.commit();
            return result;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @FunctionalInterface
    private interface JpaOperation<T> {
        T execute(EntityManager entityManager);
    }
}
