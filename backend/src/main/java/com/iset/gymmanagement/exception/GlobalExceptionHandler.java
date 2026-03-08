package com.iset.gymmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe globale de gestion des exceptions de l'application.
 * Elle intercepte les exceptions et retourne des réponses HTTP appropriées.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions lorsque la ressource demandée est introuvable.
     *
     * @param ex exception ResourceNotFoundException
     * @return réponse HTTP avec le code 404 (NOT_FOUND)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "Ressource non trouvée");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    /**
     * Gère les exceptions liées à un stock insuffisant lors d'une opération.
     *
     * @param ex exception StockUnavailableException
     * @return réponse HTTP avec le code 400 (BAD_REQUEST)
     */
    @ExceptionHandler(StockUnavailableException.class)
    public ResponseEntity<Map<String, Object>> handleStockUnavailable(StockUnavailableException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Stock insuffisant");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les exceptions lorsque le solde de la carte est insuffisant.
     *
     * @param ex exception InsufficientBalanceException
     * @return réponse HTTP avec le code 400 (BAD_REQUEST)
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientBalance(InsufficientBalanceException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Solde insuffisant");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les exceptions d'authentification lorsque l'utilisateur
     * n'est pas connecté ou fournit des identifiants invalides.
     *
     * @param ex exception UnauthorizedException
     * @return réponse HTTP avec le code 401 (UNAUTHORIZED)
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.UNAUTHORIZED.value());
        errorBody.put("error", "Non authentifié");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Gère les exceptions lorsque l'utilisateur est authentifié
     * mais n'a pas les droits nécessaires pour accéder à la ressource.
     *
     * @param ex exception ForbiddenException
     * @return réponse HTTP avec le code 403 (FORBIDDEN)
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.FORBIDDEN.value());
        errorBody.put("error", "Accès interdit");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.FORBIDDEN);
    }

    /**
     * Gère toutes les exceptions non prévues dans l'application.
     *
     * @param ex exception générique
     * @return réponse HTTP avec le code 500 (INTERNAL_SERVER_ERROR)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorBody.put("error", "Erreur serveur");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
