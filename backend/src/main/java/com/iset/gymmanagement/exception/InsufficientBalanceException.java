package com.iset.gymmanagement.exception;

/**
 * Exception levée lorsqu'une opération nécessite un solde
 * supérieur à celui disponible sur la carte de l'adhérent.
 */
public class InsufficientBalanceException extends RuntimeException {

    /**
     * Construit une exception InsufficientBalanceException avec un message explicatif.
     *
     * @param message message décrivant la raison du solde insuffisant
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
