package com.iset.gymmanagement.exception;

/**
 * Exception levée lorsqu'un utilisateur tente d'accéder
 * à une ressource sans avoir les autorisations nécessaires.
 */
public class ForbiddenException extends RuntimeException {

    /**
     * Construit une exception ForbiddenException avec un message explicatif.
     *
     * @param message message décrivant la raison de l'accès interdit
     */
    public ForbiddenException(String message) {
        super(message);
    }
}
