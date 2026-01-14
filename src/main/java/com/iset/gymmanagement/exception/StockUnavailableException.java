package com.iset.gymmanagement.exception;

/**
 * Exception levée lorsqu'une opération est impossible
 * en raison d'un stock insuffisant pour un produit.
 */
public class StockUnavailableException extends RuntimeException {

    /**
     * Construit une exception StockUnavailableException
     * avec un message décrivant le problème de stock.
     *
     * @param message message explicatif concernant le stock insuffisant
     */
    public StockUnavailableException(String message) {
        super(message);
    }
}
