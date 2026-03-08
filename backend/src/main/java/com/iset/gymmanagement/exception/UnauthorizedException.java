package com.iset.gymmanagement.exception;

/**
 * Exception levée lorsqu'un utilisateur tente
 * d'accéder à une ressource sans être authentifié.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Construit une exception UnauthorizedException
     * avec un message décrivant le problème d'authentification.
     *
     * @param message message explicatif indiquant l'absence
     *                ou l'échec de l'authentification
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
