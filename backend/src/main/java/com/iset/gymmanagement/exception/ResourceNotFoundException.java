package com.iset.gymmanagement.exception;

/**
 * Exception levée lorsqu'une ressource demandée
 * est introuvable dans le système.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construit une exception ResourceNotFoundException
     * avec un message décrivant la ressource inexistante.
     *
     * @param message message explicatif de l'erreur
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
