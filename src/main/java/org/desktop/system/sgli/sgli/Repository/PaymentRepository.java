package org.desktop.system.sgli.sgli.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;

import java.util.List;
import java.util.UUID;

public class PaymentRepository {

    public List<PaymentModel> findAll() {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            return entityManager
                    .createQuery("""
                            SELECT payment
                            FROM PaymentModel payment
                            JOIN FETCH payment.contract
                            ORDER BY payment.monthRef DESC
                            """, PaymentModel.class)
                    .getResultList();
        }
    }

    public PaymentModel save(PaymentModel payment) {
        return executeInTransaction(entityManager -> {
            payment.setContract(entityManager.merge(payment.getContract()));
            entityManager.persist(payment);
            return payment;
        });
    }

    public PaymentModel update(PaymentModel payment) {
        return executeInTransaction(entityManager -> entityManager.merge(payment));
    }

    public void delete(UUID id) {
        executeInTransaction(entityManager -> {
            PaymentModel payment = entityManager.find(PaymentModel.class, id);
            if (payment != null) {
                entityManager.remove(payment);
            }
            return null;
        });
    }

    public void deleteByContractId(UUID contractId) {
        executeInTransaction(entityManager -> {
            entityManager
                    .createQuery("DELETE FROM PaymentModel payment WHERE payment.contract.id = :contractId")
                    .setParameter("contractId", contractId)
                    .executeUpdate();
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
