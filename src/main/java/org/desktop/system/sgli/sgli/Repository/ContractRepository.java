package org.desktop.system.sgli.sgli.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
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

    public List<ContractModel> findByPag(int maxPag, int firstResult) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            TypedQuery<ContractModel> query = entityManager
                    .createQuery("SELECT contract FROM ContractModel contract ORDER BY contract.nameLocatario", ContractModel.class);
            query.setMaxResults(maxPag);
            query.setFirstResult(firstResult);
            return query.getResultList();
        }
    }

    public long countAll() {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            return entityManager
                    .createQuery("SELECT COUNT(c) FROM ContractModel c", Long.class)
                    .getSingleResult();
        }
    }


    public ContractModel save(ContractModel contract) {
        return executeInTransaction(entityManager -> {
            entityManager.persist(contract);
            return contract;
        });
    }

    public boolean existsByNameLocatario(String nameLocatario) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            Long count = entityManager
                    .createQuery("SELECT COUNT(c) FROM ContractModel c WHERE LOWER(c.nameLocatario) = LOWER(:nameLocatario)", Long.class)
                    .setParameter("nameLocatario", nameLocatario.trim())
                    .getSingleResult();
            return count > 0;
        }
    }

    public boolean existsByNameLocatarioAndId(String nameLocatario, UUID excludeId) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            Long count = entityManager
                    .createQuery("SELECT COUNT(c) FROM ContractModel c WHERE LOWER(c.nameLocatario) = LOWER(:nameLocatario) and c.id  <>: excludeId", Long.class)
                    .setParameter("nameLocatario", nameLocatario.trim())
                    .setParameter("excludeId", excludeId)
                    .getSingleResult();
            return count > 0;
        }
    }

    public boolean existsByCpfLocatario(String cpfLocatario) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            Long count = entityManager
                    .createQuery("SELECT COUNT(c) FROM ContractModel c WHERE c.cpfLocatario = :cpfLocatario", Long.class)
                    .setParameter("cpfLocatario", cpfLocatario.trim())
                    .getSingleResult();
            return count > 0;
        }
    }

    public boolean existsByCpfLocatarioAndId(String cpfLocatario, UUID excludeId) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            Long count = entityManager
                    .createQuery("SELECT COUNT(c) FROM ContractModel c WHERE c.cpfLocatario = :cpfLocatario AND c.id<> : excludeId", Long.class)
                    .setParameter("cpfLocatario", cpfLocatario.trim())
                    .setParameter("excludeId", excludeId)
                    .getSingleResult();
            return count > 0;
        }
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
